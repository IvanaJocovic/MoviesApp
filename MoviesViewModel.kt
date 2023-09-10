package com.example.moviesapplication.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.moviesapplication.networking.datasource.MoviesDataSource
import com.example.moviesapplication.ui.MoviesUi
import com.example.moviesapplication.usecase.TransformMoviesResponseToMoviesUiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val dataSource: MoviesDataSource,
    private val useCase: TransformMoviesResponseToMoviesUiUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.NoContent)
    val uiState: StateFlow<MoviesUiState> = _uiState

    var currentPage = 1

    fun showPreviousPage() {
        if(currentPage > 1){
            currentPage--
        }
        getMoviesInfo(page = currentPage)
    }

    fun showNextPage() {
        currentPage++
        getMoviesInfo(page = currentPage)
    }

    fun getMoviesInfo(page: Int) {
       viewModelScope.launch {
           _uiState.update { MoviesUiState.Loading }
           try {
               val moviesResponse = dataSource.getMovies(page = page)
               val moviesUi = useCase.invoke(moviesResponse)

               _uiState.update { MoviesUiState.Success(moviesUi) }
           } catch (e:Exception) {
               _uiState.update { MoviesUiState.Error(e) }
           }
       }
    }

    sealed class MoviesUiState {
        data class Success(val data: List<MoviesUi>): MoviesUiState()
        data class Error(val exception: Exception): MoviesUiState()
        object Loading: MoviesUiState()
        object NoContent: MoviesUiState()
    }
}