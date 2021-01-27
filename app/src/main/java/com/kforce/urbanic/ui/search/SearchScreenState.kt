package com.kforce.urbanic.ui.search

import com.kforce.urbanic.ui.core.BaseState

/**
 * Sealed class to define Events on View Model that View should react to it
 */
sealed class SearchScreenState : BaseState {
    object OnIdleScreenStateTriggered : SearchScreenState()
    object OnSearchScreenStateTriggered : SearchScreenState()
    object OnViewScreenStateTriggered : SearchScreenState()
    object OnResultRetrieving : SearchScreenState()
    object OnResultError : SearchScreenState()
}