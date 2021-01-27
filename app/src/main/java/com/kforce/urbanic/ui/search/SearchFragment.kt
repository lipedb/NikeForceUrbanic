package com.kforce.urbanic.ui.search

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.kforce.urbanic.extension.toGone
import com.kforce.urbanic.extension.toVisible
import com.kforce.urbanic.ui.core.BaseFragment
import com.kforce.urbanic.util.PATH_RESOURCE
import com.kforce.urbanic.util.URL_SEPARATOR
import com.kforce.urbanix.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.video_background.*
import java.util.*


class SearchFragment: BaseFragment<SearchViewModel>(R.layout.main_fragment, SearchViewModel::class) {

    private lateinit var screenStateObserver: Observer<SearchScreenState>

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(isVisible = false)
        setupEventObservers()
    }

    private fun setupEventObservers() {
        screenStateObserver = Observer {
            it ?: return@Observer
                when (it) {
                    SearchScreenState.OnIdleScreenStateTriggered -> setScreenAsIdle()
                    SearchScreenState.OnSearchScreenStateTriggered -> setScreenAsSearch()
                    // SearchState.OnViewStateTriggered -> TODO()
                    SearchScreenState.OnResultRetrieving -> setScreenAsLoading()
                    // SearchState.OnResultError -> TODO()
                }
            }
        viewModel.state.observe(viewLifecycleOwner, screenStateObserver)
    }

    override fun onResume() {
        super.onResume()
        setupBackgroundVideo()
        handleEnterKeyNavigation(simple_search_view)
        viewModel.onInitRoutine()
    }

    private fun setupBackgroundVideo() {
        val path = PATH_RESOURCE + requireActivity().packageName + URL_SEPARATOR + R.raw.background_video
        video_view.setVideoURI(Uri.parse(path))
        video_view.start()
        video_view.setOnCompletionListener { video_view.start() }
    }

    private fun setScreenAsLoading(){
        video_background_poster.toGone()
        loading_screen.toVisible()
    }

    private fun setScreenAsIdle(){
        search_group.toGone()
        loading_screen.toGone(false)
        floating_action.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                android.R.drawable.ic_menu_search
            )
        )
        floating_action.setOnClickListener(View.OnClickListener { viewModel.onSearchCommand() })
        floating_action.toVisible()
        video_background_poster.toVisible()
        simple_search_view.text.clear()
    }

    private fun setScreenAsSearch(){
        search_group.toVisible()
        back_button.setOnClickListener { viewModel.onSearchCancelCommand() }
        loading_screen.toGone(false)
        floating_action.toGone()
        video_background_poster.toVisible()
    }

    private fun handleEnterKeyNavigation(setterView: EditText) {
        setterView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.onSearchQueryCommand(setterView.text.toString())
            }
            handled
        })
    }

}