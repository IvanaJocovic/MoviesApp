package com.example.moviesapplication.networking.api

import com.example.moviesapplication.networking.dto.MovieResponse
import com.example.moviesapplication.networking.dto.MovieResultResponse
import com.example.moviesapplication.networking.dto.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("/titles")
    suspend fun getMovies(
        @Header("X-RapidAPI-Key") apiKey: String = "9c2e8a6f0bmsh2ff7619fd5a3b54p1e6809jsnd84553752f15",
        @Header("X-RapidAPI-Host") apiHost: String = "moviesdatabase.p.rapidapi.com",
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): MoviesResponse

    @GET("/titles/{id}")
    suspend fun getMovieById(
        @Header("X-RapidAPI-Key") apiKey: String = "9c2e8a6f0bmsh2ff7619fd5a3b54p1e6809jsnd84553752f15",
        @Header("X-RapidAPI-Host") apiHost: String = "moviesdatabase.p.rapidapi.com",
        @Path("id") id: String
    ): MovieResultResponse
}