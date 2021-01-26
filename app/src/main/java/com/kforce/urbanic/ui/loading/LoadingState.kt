package com.kforce.urbanic.ui.loading

import com.kforce.urbanic.ui.core.BaseState

/**
 * Sealed class to define Events on View Model that View should react to it
 */
sealed class LoadingState : BaseState {
    object OnConfigurationRetrieving : LoadingState()
    object OnConfigurationError : LoadingState()
    object OnConfigurationLoaded : LoadingState()
}