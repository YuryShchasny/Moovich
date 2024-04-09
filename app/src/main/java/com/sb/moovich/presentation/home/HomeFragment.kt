package com.sb.moovich.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sb.moovich.R
import com.sb.moovich.data.network.MovieApi
import com.sb.moovich.databinding.FragmentHomeBinding
import com.sb.moovich.di.MoovichApplication
import com.sb.moovich.di.ViewModelFactory
import com.sb.moovich.presentation.adapters.movies.MovieItemListAdapter
import com.sb.moovich.presentation.home.movie_info.MovieInfoFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as MoovichApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private lateinit var adapter: MovieItemListAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieItemListAdapter(requireContext())
        binding.recyclerViewRecommendations.adapter = adapter
        setClickListener()
        setObservable()
    }

    private fun setClickListener() {
        adapter.onMovieItemClickListener = { movieId ->
            findNavController().navigate(
                R.id.action_navigation_home_to_movieInfoFragment,
                MovieInfoFragment.getBundle(movieId)
            )
        }
    }

    private fun setObservable() {
        CoroutineScope(Dispatchers.IO).launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.recommendedMovies.collect {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}