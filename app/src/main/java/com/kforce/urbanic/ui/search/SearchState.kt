package com.kforce.urbanic.ui.search

import com.kforce.urbanic.ui.core.BaseState

/**
 * Sealed class to define Events on View Model that View should react to it
 */
sealed class SearchState : BaseState {
    object OnIdleStateTriggered : SearchState()
    object OnSearchStateTriggered : SearchState()
    object OnViewStateTriggered : SearchState()
    object OnConfigurationRetrieving : SearchState()
    object OnConfigurationError : SearchState()
    object OnConfigurationLoaded : SearchState()
}