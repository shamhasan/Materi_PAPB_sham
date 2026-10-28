package ih.pam.tobearsitekturpapb.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ih.pam.tobearsitekturpapb.data.repository.StudentRepository
import java.lang.reflect.Array.set

class DetailViewModel(private val repository: StudentRepository) : ViewModel() {
    var uiState by mutableStateOf(DetailUiState())
        private set

    fun loadStudent(id: Int) {
       uiState = uiState.copy(isLoading = true, errorMsg = null)

        repository.getStudentById(id){
            result -> result.onSuccess { student -> uiState = DetailUiState(student = student) }.onFailure { error -> uiState =
            DetailUiState(errorMsg = error.message ?: "Data tidak tersedia")
        }
        }
    }

    companion object {
        fun factory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(repository) as T
                }

            }
    }
}
