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
        val student = repository.getStudents();
        uiState = if (student != null) {
            HomeUiState(students = student)
        } else {
            HomeUiState(errorMsg = "Data Student Masih Kosong")
        }
    }

    companion object{
        fun factory(repository: StudentRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(repository) as T
            }
        }
    }
}