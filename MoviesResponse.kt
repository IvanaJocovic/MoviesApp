package com.example.moviesapplication.networking.dto

data class MoviesResponse(
   val results: List<MovieResponse>? = emptyList(),
   val page: Int? = null
)
