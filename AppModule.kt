package com.example.moviesapplication.di

import android.app.Application
import androidx.lifecycle.ViewModelStoreOwner
import com.example.moviesapplication.networking.api.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://moviesdatabase.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideMoviesApiService(retrofit: Retrofit) : MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }


}