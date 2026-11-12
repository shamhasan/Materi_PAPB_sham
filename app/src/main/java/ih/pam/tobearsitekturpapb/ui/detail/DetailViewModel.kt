package ih.pam.tobearsitekturpapb.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ih.pam.tobearsitekturpapb.data.repository.FavoriteRepository
import ih.pam.tobearsitekturpapb.data.repository.StudentRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.reflect.Array.set
import kotlin.coroutines.cancellation.CancellationException

class DetailViewModel(
    private val repository: StudentRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    private var favoriteJob: Job? = null
    private fun observeFavorite(id: Int) {
        favoriteJob?.cancel()
        favoriteJob = viewModelScope.launch {
            favoriteRepository.observeIsFavorite(id).collect { value ->
                _uiState.update { it.copy(isFavorite = value) }
            }
        }
    }

    fun toggleFavorite() {
        val student = uiState.value.student ?: return
        viewModelScope.launch {
            if (uiState.value.isFavorite) {
                favoriteRepository.remove(student.id)
            } else {
                favoriteRepository.add(student)
            }
        }
    }

fun loadStudent(id: Int) {

    viewModelScope.launch {
        _uiState.value = DetailUiState(isLoading = true)
        try {
            val student = repository.getStudentById(id)
            _uiState.value = DetailUiState(student = student)
            observeFavorite(id)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            _uiState.value = DetailUiState(errorMsg = e.message ?: "Gagal mengambil data")
        }
    }
}


companion object {
    fun factory(
        repository: StudentRepository,
        favoriteRepository: FavoriteRepository,
    ): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(repository, favoriteRepository) as T
            }

        }
}
}
