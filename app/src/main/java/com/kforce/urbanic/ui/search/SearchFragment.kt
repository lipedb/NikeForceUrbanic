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

    private val adapter: DefinitionListAdapter by lazy {
        DefinitionListAdapter()
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(isVisible = false)
        setupEventObservers()
        setupListeners()
        setupRecyclerView()
        viewModel.onInitRoutine()
    }

    /**
     * sets up view listeners
     */
    private fun setupListeners() {
        adapter.setOnDefinitionExpandableItemClickListener { viewModel.onDefinitionItemInteractionCommand(it) }
    }

    override fun onDestroyView() {
        onDestroyRoutine()
        super.onDestroyView()
    }

    private fun onDestroyRoutine() {
        adapter.setOnDefinitionExpandableItemClickListener(null)
        recycler_view_definition_items.adapter = null
    }

    /**
     * Setup the recycler view's adapter
     */
    private fun setupRecyclerView() {
        recycler_view_definition_items.setHasFixedSize(true)
        recycler_view_definition_items.adapter = adapter
        context?.let {
            recycler_view_definition_items.addItemDecoration(
                DefinitionItemDecoration(it, Pair(R.color.design_default_color_on_primary,R.color.design_default_color_on_secondary))
            )
        }
    }

    private fun setupEventObservers() {
        viewModel.definitionsList.observe(viewLifecycleOwner, {
            adapter.setList(it)
        })

        screenStateObserver = Observer {
            it ?: return@Observer
                when (it) {
                    SearchScreenState.OnIdleScreenStateTriggered -> setScreenAsIdle()
                    SearchScreenState.OnSearchScreenStateTriggered -> setScreenAsSearch()
                    SearchScreenState.OnViewScreenStateTriggered -> setScreenAsView()
                    SearchScreenState.OnResultRetrieving -> setScreenAsLoading()
                    SearchScreenState.OnResultError -> setScreenAsError()
                }
            }
        viewModel.state.observe(viewLifecycleOwner, screenStateObserver)
    }

    override fun onResume() {
        super.onResume()
        setupBackgroundVideo()
        handleEnterKeyNavigation(simple_search_view)
    }

    private fun setupBackgroundVideo() {
        val path = PATH_RESOURCE + requireActivity().packageName + URL_SEPARATOR + R.raw.background_video
        video_view.setVideoURI(Uri.parse(path))
        video_view.start()
        video_view.setOnCompletionListener { video_view.start() }
    }

    private fun setScreenAsLoading(){
        empty_result_placeholder.toGone()
        video_background_poster.toGone()
        loading_screen.toVisible()
    }

    private fun setScreenAsIdle(){
        recycler_view_definition_items.toGone()
        search_group.toGone()
        loading_screen.toGone(false)
        floating_action.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                android.R.drawable.ic_menu_search
            )
        )
        floating_action.setOnClickListener { viewModel.onSearchCommand() }
        floating_action.toVisible()
        video_background_poster.toVisible()
        simple_search_view.text.clear()
        empty_result_placeholder.toGone()
    }

    private fun setScreenAsSearch(){
        recycler_view_definition_items.toGone()
        search_group.toVisible()
        back_button.setOnClickListener { viewModel.onSearchCancelCommand() }
        loading_screen.toGone(false)
        floating_action.toGone()
        empty_result_placeholder.toGone()
        video_background_poster.toVisible()
    }

    private fun setScreenAsError() {
        recycler_view_definition_items.toGone()
        search_group.toGone()
        loading_screen.toGone(false)
        video_background_poster.toGone()
        empty_result_placeholder.toVisible()
        floating_action.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    android.R.drawable.ic_menu_close_clear_cancel
                )
            )
        floating_action.setOnClickListener { viewModel.onSearchCancelCommand() }
        floating_action.toVisible()
    }

    private fun setScreenAsView() {
        search_group.toVisible()
        loading_screen.toGone(false)
        video_background_poster.toGone()
        empty_result_placeholder.toGone()
        floating_action.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.ic_sort
            )
        )
        floating_action.setOnClickListener { viewModel.onSortCommand() }
        recycler_view_definition_items.toVisible()
        floating_action.toVisible()
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