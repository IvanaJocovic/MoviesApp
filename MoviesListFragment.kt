package com.example.moviesapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.databinding.FragmentMoviesListBinding
import com.example.moviesapplication.ui.MoviesUi
import com.example.moviesapplication.ui.RecyclerViewAdapter
import com.example.moviesapplication.viewmodel.MovieDetailsViewModel
import com.example.moviesapplication.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var binding: FragmentMoviesListBinding

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.getMoviesInfo(page = 1)
        setUpUi()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    Log.i("MoviesUiStateLog", "$uiState")
                    when(uiState){
                        is MoviesViewModel.MoviesUiState.Error -> Toast.makeText(context, uiState.exception.message, Toast.LENGTH_LONG).show()
                        is MoviesViewModel.MoviesUiState.Loading -> {}
                        is MoviesViewModel.MoviesUiState.NoContent -> {}
                        is MoviesViewModel.MoviesUiState.Success -> populateUi(uiState.data)
                    }
                }
            }
        }
        return view
    }

    private fun populateUi(data: List<MoviesUi>) {
        binding.textCurrent.text = data[0].page.toString()
        adapter.updateData(data)
    }

    private fun setUpUi() {
        binding.btnPreviuos.setOnClickListener {
            viewModel.showPreviousPage()
        }
        binding.btnNext.setOnClickListener {
            viewModel.showNextPage()
        }
        adapter = RecyclerViewAdapter(data = emptyList(),
            onItemClick = { movieId ->
                val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieFragment(movieId)
                findNavController().navigate(action)
        }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}