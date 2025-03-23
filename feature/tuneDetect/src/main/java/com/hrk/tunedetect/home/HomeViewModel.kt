package com.hrk.tunedetect.home

import com.hrk.apps.hrkdev.core.data.repository.SearchingRepository
import com.hrk.apps.hrkdev.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchingRepository: SearchingRepository,
) : BaseViewModel<HomeEventUiState, HomeEvent>(HomeEventUiState()) {

    override fun handleEvent(event: HomeEvent) {

    }
}

sealed interface HomeEvent {}

data class HomeEventUiState(
    val shouldShowPopUpGoToSetting: Boolean = false,
)