package com.example.moviesapplication

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapplication.databinding.FragmentMovieBinding
import com.example.moviesapplication.ui.MovieUi
import com.example.moviesapplication.ui.MoviesUi
import com.example.moviesapplication.ui.RecyclerViewAdapter
import com.example.moviesapplication.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: FragmentMovieBinding
    private val movieArgs: MovieFragmentArgs by navArgs()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMovieInfo(id = movieArgs.movieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    Log.i("MovieUiState","$uiState")
                    when(uiState){
                        is MovieDetailsViewModel.MovieUiState.Error -> Toast.makeText(context, uiState.exception.message, Toast.LENGTH_LONG).show()
                        is MovieDetailsViewModel.MovieUiState.NoContent -> {}
                        is MovieDetailsViewModel.MovieUiState.Loading -> {}
                        is MovieDetailsViewModel.MovieUiState.Success -> populateUi(uiState.data)
                    }
                }
            }
        }
    }

    private fun populateUi(data: MovieUi) {
        binding.txtTitle.text = data.text
        binding.txtReleaseYear.text = data.releaseYear
        binding.txtEndYear.text = data.endYear
        binding.txtDay.text = data.day
        binding.txtMonth.text = data.month
        binding.txtYear.text = data.year
        Glide
            .with(requireContext())
            .load(data.url)
            .into(binding.imgMovie)
    }
}