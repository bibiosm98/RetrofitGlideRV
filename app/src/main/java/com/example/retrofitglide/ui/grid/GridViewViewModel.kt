package com.example.retrofitglide.ui.grid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitglide.network.Comic
import com.example.retrofitglide.network.ComicApiFilter
import com.example.retrofitglide.network.MarvelApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MarvelApiStatus { LOADING, ERROR, DONE}
class GridViewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        _text.value = "Welcome in Grid View Fragment"
    }

    private val _status = MutableLiveData<MarvelApiStatus>()
    val status: LiveData<MarvelApiStatus>
        get() = _status

    private val _comicList = MutableLiveData<List<Comic>>()
    val comicList: LiveData<List<Comic>>
        get() = _comicList

    private val _comic = MutableLiveData<Comic>()
    val comic: LiveData<Comic>
        get() = _comic
    private val _comic2 = MutableLiveData<String>()
    val comic2: LiveData<String>
        get() = _comic2


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getMarvelAppComics(ComicApiFilter.SHOW_ALL)
    }

    private val _navigateToSelectedComic = MutableLiveData<Comic>()
    val navigateToSelectedComic: LiveData<Comic>
         get() = _navigateToSelectedComic

    // get link from API
    private fun getMarvelAppComics(filter: ComicApiFilter?){
        coroutineScope.launch {
//            val getPropertyDeferred = MarvelApi.retrofitService.getComics(filter.value)
            val getPropertyDeferred = MarvelApi.retrofitService.getComics("1", "080a502746c8a60aeab043387a56eef0", "6edc18ab1a954d230c1f03c590d469d2", filter?.value)
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
        getMarvelAppComics(filter)
    }
}