package com.kforce.urbanic.ui.today

import com.kforce.urbanic.ui.core.BaseFragment
import com.kforce.urbanix.R

class TodayFragment : BaseFragment<TodayViewModel>(R.layout.main_fragment, TodayViewModel::class) {

    companion object {
        fun newInstance() = TodayFragment()
    }

}