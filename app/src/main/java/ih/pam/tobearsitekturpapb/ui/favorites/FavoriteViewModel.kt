package ih.pam.tobearsitekturpapb.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ih.pam.tobearsitekturpapb.data.preferences.UserPreferencesRepository
import ih.pam.tobearsitekturpapb.data.repository.FavoriteRepository
import ih.pam.tobearsitekturpapb.ui.home.HomeUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoriteRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()



    init {
        fetchAllFavorites()
    }

    fun changeSortOrder() {
        val nextOrder = if (_uiState.value.sortOrder == "ASC") "DESC" else "ASC"
        viewModelScope.launch {
            preferencesRepository.saveLastSortOrder(nextOrder)
        }
    }

    fun fetchAllFavorites() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                combine(
                    repository.observeFavorites(),
                    preferencesRepository.lastSortOrder
                ) { students, order ->
                    val sortedStudents = if (order == "ASC") {
                        students.sortedBy { it.name.lowercase() }
                    } else {
                        students.sortedByDescending { it.name.lowercase() }
                    }
                    Pair(sortedStudents, order)
                }.collect { (sortedStudents, order) ->
                    _uiState.update {
                        it.copy(
                            students = sortedStudents,
                            sortOrder = order,
                            isEmpty = sortedStudents.isEmpty(),
                            isLoading = false,
                            errorMsg = null
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMsg = e.message ?: "Gagal mengambil data"
                    )
                }
            }
        }
    }

    companion object {
        fun factory(repository: FavoriteRepository, preferencesRepository: UserPreferencesRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavoriteViewModel(repository, preferencesRepository) as T
                }
            }
    }
}