package com.gesvik.movieapp.detailFilm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.gesvik.movieapp.R
import com.gesvik.movieapp.network.entities.*
import com.gesvik.movieapp.filmOverview.FilmsApiStatus
import com.gesvik.movieapp.network.FilmApi
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailFilmViewModel(filmsProperty: FilmsItem, app: Application) : AndroidViewModel(app) {
    private val _status = MutableLiveData<FilmsApiStatus>()
    val status: LiveData<FilmsApiStatus>
        get() = _status

    private val _filmProperty = MutableLiveData<FilmItemProperty>()
    val filmItemProperty: LiveData<FilmItemProperty>
        get() = _filmProperty

//    private val _filmDataProperty = MutableLiveData<Data>()
//    val filmDataProperty: LiveData<Data>
//        get() = _filmDataProperty

    private val _selectedProperty = MutableLiveData<FilmsItem>()
    val selectedProperty: LiveData<FilmsItem>
        get() = _selectedProperty

    private val _staffProperty = MutableLiveData<List<StaffProperty>>()
    val staffProperty: LiveData<List<StaffProperty>>
        get() = _staffProperty

    private val _selectedStaffProperty = MutableLiveData<StaffProperty>()
    val selectedStaffProperty: LiveData<StaffProperty>
        get() = _selectedStaffProperty

    init {
        _selectedProperty.value = filmsProperty

        getFilmProperties()
        getStaffProperties()
    }

    val displayPropertyFilmLength = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            R.string.displayLengthFilm,
            it.mFilmLength!!.split(":")[0], it.mFilmLength!!.split(":")[1]
        )
    }

    val displayPropertyRating = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            R.string.displayRating,
            it.mRating!!
        )
    }

    val displayPropertyAgeLimits = Transformations.map(filmItemProperty) {
        var string: String = it.data.ratingMpaa!!
        if (!string.contains("-")) {
            string += "-" + it.data.mRatingAgeLimits
        }
        app.applicationContext.getString(
            R.string.displayAgeLimits,
            string
        )
    }

    private fun getFilmProperties() {
        viewModelScope.launch {
            _status.value = FilmsApiStatus.LOADING

            try {
                _filmProperty.value =
                    FilmApi.retrofitService.getFilmItem(_selectedProperty.value!!.filmId)
                _status.value = FilmsApiStatus.DONE

                Timber.d("FILM PROPERTY VALUE DONE")
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = FilmsApiStatus.ERROR
            }
        }
    }

    private fun getStaffProperties() {
        viewModelScope.launch {
            _status.value = FilmsApiStatus.LOADING

            try {
                _staffProperty.value =
                    FilmApi.retrofitService.getStaff(_selectedProperty.value!!.filmId)
                _status.value = FilmsApiStatus.DONE

                Timber.d("STAFF PROPERTY VALUE DONE")
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = FilmsApiStatus.ERROR
                _staffProperty.value = ArrayList()
            }
        }
    }

}