package com.test.italika.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.italika.base.BaseViewModel
import com.test.italika.models.Movie
import com.test.italika.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val loader: MoviesRepository
) : BaseViewModel() {
    private val moviesListMutable = MutableLiveData<ArrayList<Movie>>()
    private lateinit var viewModelJob: Job

    fun loadData(category: String) {
        _loading.postValue(true)
        viewModelJob = viewModelScope.launch {
            val results = loader.getMovieList(category)
            moviesListMutable.postValue(ArrayList(results))
            _loading.postValue(false)
        }
    }

    fun getMoviesList(): LiveData<ArrayList<Movie>> = moviesListMutable

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}