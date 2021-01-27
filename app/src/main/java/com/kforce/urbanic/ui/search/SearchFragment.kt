package com.kforce.urbanic.ui.search

import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.kforce.urbanic.ui.core.BaseFragment
import com.kforce.urbanic.util.PATH_RESOURCE
import com.kforce.urbanic.util.URL_SEPARATOR
import com.kforce.urbanix.R
import kotlinx.android.synthetic.main.video_background.*


class SearchFragment: BaseFragment<SearchViewModel>(R.layout.main_fragment, SearchViewModel::class) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(isVisible = false)
    }

    override fun onResume() {
        super.onResume()
        setupBackgroundVideo()
        viewModel.onInitRoutine()
    }

    private fun setupBackgroundVideo() {
        val path = PATH_RESOURCE + requireActivity().packageName + URL_SEPARATOR + R.raw.background_video
        video_view.setVideoURI(Uri.parse(path))
        video_view.start()
        video_view.setOnCompletionListener { video_view.start() }
    }
}