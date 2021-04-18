package com.example.retrofitglide.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitglide.network.Comic

class DetailViewModelFactory(
    private val selectedComic: Comic,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(selectedComic, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}