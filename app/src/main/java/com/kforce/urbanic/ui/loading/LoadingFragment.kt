package com.kforce.urbanic.ui.loading

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.kforce.urbanic.extension.toGone
import com.kforce.urbanic.extension.toVisible
import com.kforce.urbanic.ui.core.BaseFragment
import com.kforce.urbanic.ui.search.SearchFragment
import com.kforce.urbanix.R
import kotlinx.android.synthetic.main.loading_fragment.*

class LoadingFragment : BaseFragment<LoadingViewModel>(R.layout.loading_fragment, LoadingViewModel::class) {

    private lateinit var stateObserver: Observer<LoadingState>

    companion object {
        fun newInstance() = LoadingFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(isVisible = false)
        setupEventObservers()
    }

    private fun setupEventObservers() {
        stateObserver = Observer {
            it ?: return@Observer
            when (it) {
               is LoadingState.OnConfigurationLoaded -> navigateTodayFragment()
               is LoadingState.OnConfigurationRetrieving -> setLoadingState(isLoading = true)
               is LoadingState.OnConfigurationError -> navigateToError()
            }
        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)
    }

    private fun navigateTodayFragment() {
        setLoadingState(isLoading = false)
        navigateToFragment(SearchFragment.newInstance())
    }

    private fun navigateToError() {
        setLoadingState(isLoading = false)
        //TODO: Implement redirect to error Screen
    }

    private fun setLoadingState(isLoading : Boolean = false) = when {
        isLoading -> {
            progress_bar.toVisible(animate = false)
            loading_text_view.toVisible(animate = false)
        }
        else -> {
            progress_bar.toGone(animate = false)
            loading_text_view.toGone(animate = false)
        }
    }

}