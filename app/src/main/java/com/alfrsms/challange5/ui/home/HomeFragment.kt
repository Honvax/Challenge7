package com.alfrsms.challange5.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alfrsms.challange5.R
import com.alfrsms.challange5.data.network.model.HomeMovie
import com.alfrsms.challange5.databinding.FragmentHomeBinding
import com.alfrsms.challange5.di.MovieServiceLocator
import com.alfrsms.challange5.di.UserServiceLocator
import com.alfrsms.challange5.ui.home.adapter.HomeAdapter
import com.alfrsms.challange5.ui.profile.ProfileFragment
import com.alfrsms.challange5.utils.viewModelFactory
import com.alfrsms.challange5.wrapper.Resource


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModelFactory {
        HomeViewModel(
            MovieServiceLocator.provideMovieRepository(requireContext()),
            UserServiceLocator.provideUserRepository(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInitialUser()
        observeData()
    }

    private fun getInitialUser() {
        val userId = viewModel.getUserId()
        Log.d("inituser", userId.toString())
        viewModel.getUserById(userId)
        viewModel.userByIdResult.observe(viewLifecycleOwner) {
            it?.let { binding.tvUsername.text = it.username
                Log.d("inituser", it.username.toString())}
        }
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.profileFragment) {
                ProfileFragment().arguments = arguments
            }
        }
    }

    private fun setHomeRecyclerView(movie: List<HomeMovie>?) {
        val adapter = HomeAdapter()
        adapter.submitList(movie)
        adapter.itemClickListener = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
            findNavController().navigate(action)
        }

        binding.apply {
            rvHomeList.layoutManager = LinearLayoutManager(requireContext())
            rvHomeList.adapter = adapter
        }
        viewModel.listStateParcel?.let { parcelable ->
            binding.rvHomeList.layoutManager?.onRestoreInstanceState(parcelable)
            viewModel.listStateParcel = null
        }
    }

    private fun observeData() {
        viewModel.homeMovieListResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading<*> -> {
                    setLoadingState(true)
                    setErrorState(false)
                }
                is Resource.Error<*> -> {
                    setLoadingState(true)
                    setErrorState(true, it.exception.toString())
                }
                is Resource.Success<*> -> {
                    setLoadingState(false)
                    setErrorState(false)
                    setHomeRecyclerView(it.payload)
                }
                else -> {

                }
            }
        }
    }

    private fun setErrorState(isError: Boolean, message: String? = "") {
        binding.tvError.isVisible = isError
        binding.tvError.text = message
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.pbHomeList.isVisible = isLoading
        binding.rvHomeList.isVisible = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}