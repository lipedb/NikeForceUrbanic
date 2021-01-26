package com.kforce.urbanic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kforce.urbanic.ui.core.BaseActivity
import com.kforce.urbanic.ui.loading.LoadingFragment
import com.kforce.urbanix.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoadingFragment.newInstance())
                    .commitNow()
        }
    }
}