package com.kforce.urbanic.ui.loading

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kforce.urbanic.domain.case.settings.GetSettingsUseCase
import com.kforce.urbanic.repository.content.ContentImplementation
import com.kforce.urbanic.service.content.ContentService
import com.kforce.urbanic.ui.today.TodayViewModel
import com.kforce.urbanic.util.onFailure
import com.kforce.urbanic.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoadingViewModel : ViewModel() {

    val state: MutableLiveData<LoadingState> = MutableLiveData()

    init {
        updateState(LoadingState.OnConfigurationRetrieving)
        getBaseSettings()
    }

    private fun getBaseSettings() {
        val service = ContentService()
        val getSettingsUseCase = GetSettingsUseCase(service)
        getSettingsUseCase.invoke(viewModelScope, null) {
            it.onFailure { updateState(LoadingState.OnConfigurationError) }
            it.onSuccess { updateState(LoadingState.OnConfigurationLoaded) }
        }
    }

    private fun updateState(newState: LoadingState){
        state.postValue(newState)
    }
}