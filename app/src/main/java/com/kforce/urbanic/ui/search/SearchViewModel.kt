package com.kforce.urbanic.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kforce.urbanic.domain.case.definitions.GetDefinitionsUseCase
import com.kforce.urbanic.enum.SearchOperationType
import com.kforce.urbanic.service.content.ContentService
import com.kforce.urbanic.util.EMPTY_STRING
import com.kforce.urbanic.util.onFailure
import com.kforce.urbanic.util.onSuccess


class SearchViewModel: ViewModel() {

    val operationType: MutableLiveData<SearchOperationType> = MutableLiveData()
    val state: MutableLiveData<SearchScreenState> = MutableLiveData()

    //region Navigation

    /**
     * This command is used on Toolbar Search Button Interaction
     */
    fun onSearchCommand() {
        switchCurrentOperation(SearchOperationType.SEARCH)
        updateState(SearchScreenState.OnSearchScreenStateTriggered)
    }

    /**
     * This command is used on Toolbar Search Text Changed
     */
    fun onSearchQueryCommand(query: String) {
        when { query != EMPTY_STRING -> { startSearch(query) } }
    }

    /**
     * This command is used on SearchBar Left Button Interaction
     */
    fun onSearchCancelCommand() {
        switchCurrentOperation(SearchOperationType.IDLE)
        updateState(SearchScreenState.OnIdleScreenStateTriggered)
    }
    //endregion

    //region State Control
    private fun switchCurrentOperation(currentOperation: SearchOperationType) {
        operationType.postValue(currentOperation)
    }

    private fun updateState(newState: SearchScreenState){
        state.postValue(newState)
    }
    //endregion

    //region Initialization Routine
    fun onInitRoutine() {
        operationType.value = SearchOperationType.IDLE
        updateState(SearchScreenState.OnIdleScreenStateTriggered)
    }
    //endregion

    //region Main Function
    private fun startSearch(query: String){
        updateState(SearchScreenState.OnResultRetrieving)
        val service = ContentService()
        val getDefinitionsUseCase = GetDefinitionsUseCase(service, query)
        getDefinitionsUseCase.invoke(viewModelScope, null) {
            it.onFailure { updateState(SearchScreenState.OnResultError) }
            it.onSuccess { updateState(SearchScreenState.OnIdleScreenStateTriggered) }
        }
    }

    //endregion

}