package com.kforce.urbanic.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kforce.urbanic.repository.content.ContentImplementation
import com.kforce.urbanic.service.content.ContentService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.function.Consumer


class MainViewModel : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val service = ContentService()
            val request = ContentImplementation(service).fetchAppSettings()
            Log.d("DOWNLOADED CONFIG", request.throwable.toString())
        }
    }
}