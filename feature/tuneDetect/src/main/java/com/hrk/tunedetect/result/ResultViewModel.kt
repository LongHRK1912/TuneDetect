package com.hrk.tunedetect.result

import android.util.Log
import com.hrk.apps.hrkdev.core.data.repository.SearchingRepository
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.apps.hrkdev.core.model.spotify.TrackDetailResponse
import com.hrk.apps.hrkdev.core.model.spotify.TracksItem
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.core.utils.JSON.decodeTo
import com.hrk.apps.hrkdev.core.utils.JSON.toJson
import com.hrk.apps.hrkdev.core.utils.ResultWrapper
import com.hrk.apps.hrkdev.core.viewmodel.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel(assistedFactory = ResultViewModel.Factory::class)
class ResultViewModel @AssistedInject constructor(
    @Assisted private val acrCloud: ACRCloudResponse?,
    private val searchingRepository: SearchingRepository,
) : BaseViewModel<ResultEventUiState, ResultEvent>(ResultEventUiState()) {

    init {
        handleEvent(
            ResultEvent.GetTrackInformationFromSpotify
        )
    }

    @AssistedFactory
    fun interface Factory {
        fun create(acrCloud: ACRCloudResponse?): ResultViewModel
    }

    override fun handleEvent(event: ResultEvent) {
        when (event) {
            ResultEvent.GetTrackInformationFromSpotify -> getTrackInformationFromSpotify()
        }
    }

    private fun getTrackInformationFromSpotify() {
        async {
            val trackId =
                acrCloud?.metadata?.music?.firstOrNull()?.external_metadata?.spotify?.track?.id
                    ?: return@async

            AuthService.authSpotify?.let {
                searchingRepository.trackDetail(
                    auth = it.getToken(),
                    trackId = trackId,
                ).collect { response ->
                    when (response) {
                        is ResultWrapper.Success -> {
                            updateUiState(
                                uiState = uiState.value.copy(
                                    track = response.toJson().decodeTo(TrackDetailResponse::class.java)?.value
                                )
                            )
                        }

                        is ResultWrapper.Error -> {
                        }

                        ResultWrapper.Loading -> {

                        }
                    }
                }
            }
        }
    }
}

sealed interface ResultEvent {
    data object GetTrackInformationFromSpotify : ResultEvent
}

data class ResultEventUiState(
    val track: TracksItem? = null,
)