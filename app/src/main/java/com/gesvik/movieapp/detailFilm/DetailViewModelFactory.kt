package com.gesvik.movieapp.detailFilm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gesvik.movieapp.network.entities.FilmsItem

class DetailViewModelFactory(private val filmsProperty: FilmsItem, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailFilmViewModel::class.java)) {
            return DetailFilmViewModel(filmsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
