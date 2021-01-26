package com.kforce.urbanic.ui.core

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    /**
     * Set ToolBar
     * @param isVisible: for tool bar visibility
     * */
    fun setToolBar(isVisible: Boolean = false) {
        when {
            isVisible -> supportActionBar?.show()
            else -> supportActionBar?.hide()
        }
    }
}