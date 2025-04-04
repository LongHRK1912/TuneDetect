package com.hrk.apps.hrkdev.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class BaseViewModel<State : Any, Event>(initialState: State) : ViewModel() {
    private val handler = CoroutineExceptionHandler { _, exception ->

    }
    private val viewModelJob = SupervisorJob()
    val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob + handler)
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob + handler)
    val defaultScope = CoroutineScope(Dispatchers.Default + viewModelJob + handler)

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    open fun handleEvent(event: Event) {
        // function for handle view's event
    }

    open fun updateUiState(uiState: State) {
        _uiState.value = uiState
    }

    open fun updateUiStateAtomic(uiState: State) {
        _uiState.update {
            uiState
        }
    }

    protected inline fun async(crossinline block: suspend () -> Unit) =
        ioScope.launch {
            block()
        }

    protected inline fun asyncUi(crossinline block: suspend () -> Unit) =
        uiScope.launch {
            block()
        }

    protected inline fun asyncDefault(crossinline block: suspend () -> Unit) =
        defaultScope.launch {
            block()
        }
}