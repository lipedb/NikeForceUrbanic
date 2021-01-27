package com.kforce.urbanic.ui.core

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass


/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment<VM : ViewModel>(@LayoutRes val layout: Int, private val viewModelClass: KClass<VM>) : Fragment() {

    open lateinit var viewModel: VM

    protected val parentActivity by lazy { activity as BaseActivity }


    /**
     * set toolbar visibility and background image
     *
     * @param isVisible visibility of the toolbar
     */
    fun setToolbar(isVisible: Boolean) {
        parentActivity.setToolBar(isVisible)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(viewModelClass.java)
    }

    fun navigateToFragment(targetFragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(this.id, targetFragment)
        transaction.commit()
    }

}