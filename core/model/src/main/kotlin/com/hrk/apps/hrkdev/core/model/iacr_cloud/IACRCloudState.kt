package com.hrk.apps.hrkdev.core.model.iacr_cloud

sealed class IACRCloudState {
    data object Nothing : IACRCloudState()
    data object Recording : IACRCloudState()
    data class Success(val result: ACRCloudResponse) : IACRCloudState()
    data class Error(val message: String) : IACRCloudState()
}