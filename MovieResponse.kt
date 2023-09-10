package com.example.moviesapplication.networking.dto

data class MovieResponse(
    val id: String? = null,
    val primaryImage: PrimaryImageResponse? = null,
    val titleText: TitleTextResponse? = null,
    val releaseYear: ReleaseYearResponse? = null,
    val releaseDate: ReleaseDateResponse? = null
)
