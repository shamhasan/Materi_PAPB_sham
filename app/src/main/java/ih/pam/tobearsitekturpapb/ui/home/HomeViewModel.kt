package ih.pam.tobearsitekturpapb.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ih.pam.tobearsitekturpapb.data.repository.StudentRepository

class HomeViewModel(
    private val repository: StudentRepository,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        fetchAllStudents()
    }

    fun fetchAllStudents() {
        uiState = uiState.copy(isLoading = true, errorMsg = null)
        repository.getStudents { result ->
            result.onSuccess { data -> uiState = HomeUiState(students = data) }.onFailure { error ->
                uiState = HomeUiState(errorMsg = error.message ?: "Gagal mengambil data")
            }
        }
    }

    fun searchStudents(query: String) {
        if (query.isBlank()) {
            fetchAllStudents()
            return
        }

        uiState = uiState.copy(
            isLoading = true,
            errorMsg = null
        )
        repository.searchStudents(query = query) { result ->
            result.onSuccess { data ->
                if (data == null || data.isEmpty()) {
                    uiState = uiState.copy(
                        isLoading = false,
                        isEmpty = true
                    )
                    return@onSuccess
                }
                uiState = uiState.copy(
                    isLoading = false,
                    students = data
                )
            }.onFailure { error ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMsg = error.message ?: "Gagal mengambil data"
                )
            }
        }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(repository) as T
                }
            }
    }
}

