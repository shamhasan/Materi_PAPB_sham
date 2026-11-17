package ih.pam.tobearsitekturpapb.ui.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ih.pam.tobearsitekturpapb.data.location.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<LocationUiState>(LocationUiState.Idle)
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    fun refreshLocation() {
        viewModelScope.launch {
            _uiState.value = LocationUiState.Loading
            _uiState.value = locationRepository.getCurrentLocation().fold(
                onSuccess = { LocationUiState.Success(it) },
                onFailure = { LocationUiState.Error(it.message ?: "Gagal mengambil lokasi") }
            )
        }
    }

    companion object {
        fun factory(locationRepository: LocationRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LocationViewModel(locationRepository) as T
                }
            }
    }
}
