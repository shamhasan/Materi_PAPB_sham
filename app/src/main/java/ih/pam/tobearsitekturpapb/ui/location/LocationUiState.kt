package ih.pam.tobearsitekturpapb.ui.location

import ih.pam.tobearsitekturpapb.data.location.DeviceLocation

sealed interface LocationUiState {
    data object Idle: LocationUiState
    data object Loading: LocationUiState
    data class Success(val location: DeviceLocation): LocationUiState
    data class Error(val message: String): LocationUiState
}