package com.example.moviesapplication.networking.datasource

import com.example.moviesapplication.networking.api.MoviesApiService
import com.example.moviesapplication.networking.dto.MovieResponse
import com.example.moviesapplication.networking.dto.MoviesResponse
import javax.inject.Inject

class MoviesDataSource @Inject constructor(
    private val apiService: MoviesApiService
) {
    suspend fun getMovies(page: Int): MoviesResponse {
        return apiService.getMovies(
            page = page
        )
    }

    suspend fun getMovieById(id: String): MovieResponse {
        return apiService.getMovieById(
            id = id
        ).results
    }
}