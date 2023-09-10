package com.example.moviesapplication.usecase

import android.util.Log
import com.example.moviesapplication.networking.dto.MovieResponse
import com.example.moviesapplication.networking.dto.MoviesResponse
import com.example.moviesapplication.ui.MovieUi
import com.example.moviesapplication.ui.MoviesUi
import javax.inject.Inject

class TransformMoviesResponseToMoviesUiUseCase @Inject constructor() {

    operator fun invoke(response: MoviesResponse) : List<MoviesUi> {
        return response.results?.map { result ->
            MoviesUi(
                url = result.primaryImage?.url ?: "",
                text = result.titleText?.text ?: "",
                year = result.releaseYear?.year ?: "",
                endYear = result.releaseYear?.endYear ?: "",
                page = response.page ?: 0,
                id = result.id ?: ""
            )
        } ?: emptyList()
    }

    operator fun invoke(response: MovieResponse) : MovieUi {
        Log.i("UseCaseResponse", "$response")
        return MovieUi(
            url = response.primaryImage?.url ?: "",
            text = response.titleText?.text ?: "",
            releaseYear = response.releaseYear?.year ?: "",
            endYear = response.releaseYear?.endYear ?: "",
            day = response.releaseDate?.day ?: "",
            month = response.releaseDate?.month ?: "",
            year = response.releaseDate?.year ?: "",
            id = response.id ?: ""
        )
    }
}