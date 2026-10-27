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
        val studentRep = repository.getStudentById(id)

        uiState = if (studentRep != null) {
            DetailUiState(student = studentRep)
        } else {
            DetailUiState(errorMsg = "Data Tidak Ditemukan")
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
