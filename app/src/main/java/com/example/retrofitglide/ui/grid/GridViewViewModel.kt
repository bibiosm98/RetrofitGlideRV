package com.example.retrofitglide.ui.grid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitglide.network.Comic
import com.example.retrofitglide.network.MarvelApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GridViewViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        _text.value = "Welcome in Griw View Fragment"
    }

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
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
        getMarvelAppComics()
    }

    // get link from API
    private fun getMarvelAppComics(){
        coroutineScope.launch {
            val getPropertyDeferred = MarvelApi.retrofitService.getComics()
            try{
                val listResult = getPropertyDeferred.await()
                _status.value = "WITAMY"
                if(listResult.data.total > 0){
//                    listResult.data.results[4].thumbnail.path = listResult.data.results[4].thumbnail.path.plus("/portrait_xlarge.jpg")
                    listResult.data.results[4].thumbnail.path = listResult.data.results[4].thumbnail.path.plus("/portrait_uncanny.jpg")
                    _comic.value = listResult.data.results[4]
                    _comic2.value = listResult.data.results[4].thumbnail.path
                    Log.i("HomeVM 2", _comic.value!!.thumbnail.path + " 2x  " + listResult.data.results[4].thumbnail.path)
                }
            }catch (t: Throwable) {
                _status.value = "Failure: " + t.message
            }
        }
    }
}