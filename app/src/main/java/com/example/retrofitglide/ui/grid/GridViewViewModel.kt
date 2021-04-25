package com.example.retrofitglide.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitglide.BuildConfig
import com.example.retrofitglide.network.Comic
import com.example.retrofitglide.network.ComicApiFilter
import com.example.retrofitglide.network.MarvelApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarvelApiStatus { LOADING, ERROR, DONE}
class GridViewViewModel : ViewModel() {

    private val _status = MutableLiveData<MarvelApiStatus>()
    val status: LiveData<MarvelApiStatus>
        get() = _status

    private val _limit = MutableLiveData<Int>()
    val limit: LiveData<Int>
        get() = _limit

    private val _comicList = MutableLiveData<List<Comic>>()
    val comicList: LiveData<List<Comic>>
        get() = _comicList

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMarvelAppComics(ComicApiFilter.SHOW_ALL)
        _limit.value = 15;
    }

    private val _navigateToSelectedComic = MutableLiveData<Comic>()
    val navigateToSelectedComic: LiveData<Comic>
         get() = _navigateToSelectedComic

    // get link from API
    private fun getMarvelAppComics(filter: ComicApiFilter?){
        coroutineScope.launch {
            val getPropertyDeferred = MarvelApi.retrofitService.getComics(
                BuildConfig.apiTimestamp,
                BuildConfig.apiKey,
                BuildConfig.apiHash,
                filter?.value,
                _limit.value
            )
            try{
                _status.value = MarvelApiStatus.LOADING
                val listResult = getPropertyDeferred.await()
                if(listResult.data.total > 0){
                    _status.value = MarvelApiStatus.DONE
                    _comicList.value = listResult.data.results
                }
            }catch (t: Throwable) {
                _status.value = MarvelApiStatus.ERROR
                _comicList.value = ArrayList()
            }
        }
    }

    fun displayComicDetails(comic: Comic) {
        _navigateToSelectedComic.value = comic
    }

    fun displayComicDetailsComplete() {
        _navigateToSelectedComic.value = null
    }

    fun updateFilter(filter: ComicApiFilter) {
        _limit.value = 100;
        getMarvelAppComics(filter)
    }
}