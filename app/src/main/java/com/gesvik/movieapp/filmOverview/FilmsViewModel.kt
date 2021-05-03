package com.gesvik.movieapp.filmOverview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gesvik.movieapp.network.entities.FilmsItem
import com.gesvik.movieapp.network.FilmApi
import com.gesvik.movieapp.network.entities.FilmsProperty
import com.gesvik.movieapp.network.entities.GenresResponse
import kotlinx.coroutines.launch

enum class FilmsApiStatus { LOADING, ERROR, DONE }

class FilmsViewModel(categoryName: String) : ViewModel() {
    private val _status = MutableLiveData<FilmsApiStatus>()
    val status: LiveData<FilmsApiStatus>
        get() = _status

    private val _category = MutableLiveData<String>()
    val category: LiveData<String>
        get() = _category

    private val _awaitProperties = MutableLiveData<FilmsProperty>()
    val properties: LiveData<FilmsProperty>
        get() = _awaitProperties

    private val _genresProperties = MutableLiveData<GenresResponse>()
    val genresProperties: LiveData<GenresResponse>
        get() = _genresProperties

    private val _navigateToSelectedProperty = MutableLiveData<FilmsItem?>()
    val navigateToSelectedProperty: MutableLiveData<FilmsItem?>
        get() = _navigateToSelectedProperty

    init {
        _category.value = categoryName
        getFilmsProperties()
        getGenresProperties()
    }

    fun displayPropertyDetails(filmProperty: FilmsItem) {
        _navigateToSelectedProperty.value = filmProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    private fun getFilmsProperties() {
        viewModelScope.launch {
            _status.value = FilmsApiStatus.LOADING

            try {
                _awaitProperties.value = FilmApi.retrofitService.getFilms(_category.value!!)
                _status.value = FilmsApiStatus.DONE

                Log.d("FilmsViewModel", "FILMS PROPERTY VALUE DONE")
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = FilmsApiStatus.ERROR
            }
        }
    }

    private fun getGenresProperties() {
        viewModelScope.launch {
            _status.value = FilmsApiStatus.LOADING

            try {
                _genresProperties.value = FilmApi.retrofitService.getGenres()
                _status.value = FilmsApiStatus.DONE

                Log.d("FilmsViewModel", "GENRES PROPERTY VALUE DONE")
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = FilmsApiStatus.ERROR
            }
        }
    }
}