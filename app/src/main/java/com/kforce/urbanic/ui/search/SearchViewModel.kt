package com.kforce.urbanic.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kforce.urbanic.enum.SearchOperationType
import com.kforce.urbanic.util.EMPTY_STRING


class SearchViewModel: ViewModel() {

    val operationType: MutableLiveData<SearchOperationType> = MutableLiveData()

    //region Navigation

    /**
     * This command is used on Toolbar Search Button Interaction
     */
    fun onSearchCommand() {
        switchCurrentOperation(SearchOperationType.SEARCH)

    }

    /**
     * This command is used on Toolbar Search Text Changed
     */
    fun onSearchQueryCommand(query: String) {
        when (query) {
            EMPTY_STRING -> {
                switchCurrentOperation(SearchOperationType.SEARCH)

            }
            else -> {
                switchCurrentOperation(SearchOperationType.VIEW)

            }
        }
    }

    /**
     * This command is used on SearchBar Left Button Interaction
     */
    fun onSearchBarBackCommand() {
        switchCurrentOperation(SearchOperationType.IDLE)

    }
    //endregion

    //region State Control
    private fun switchCurrentOperation(currentOperation: SearchOperationType) {
        operationType.postValue(currentOperation)
    }
    //endregion

    //region Initialization Routine
    fun onInitRoutine() {
        operationType.value = SearchOperationType.IDLE
    }
    //endregion

}