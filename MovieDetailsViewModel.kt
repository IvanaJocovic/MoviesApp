package com.example.moviesapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.networking.datasource.MoviesDataSource
import com.example.moviesapplication.ui.MovieUi
import com.example.moviesapplication.ui.MoviesUi
import com.example.moviesapplication.usecase.TransformMoviesResponseToMoviesUiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val dataSource: MoviesDataSource,
    private val useCase: TransformMoviesResponseToMoviesUiUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.NoContent)
    val uiState: StateFlow<MovieUiState> = _uiState

    fun getMovieInfo(id: String){
        viewModelScope.launch {
            _uiState.update { MovieUiState.Loading }
            try {
                val movieResponse = dataSource.getMovieById(id = id)
                val movieUi = useCase.invoke(movieResponse)

                _uiState.update { MovieUiState.Success(movieUi) }
            } catch (e:Exception) {
                _uiState.update { MovieUiState.Error(e) }
            }
        }
    }

    sealed class MovieUiState {
        data class Success(val data: MovieUi): MovieUiState()
        data class Error(val exception: Exception): MovieUiState()
        object Loading: MovieUiState()
        object NoContent: MovieUiState()
    }

}