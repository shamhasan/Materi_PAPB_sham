package ih.pam.tobearsitekturpapb.ui.favorites

import ih.pam.tobearsitekturpapb.data.model.Student
import kotlinx.coroutines.flow.Flow

data class FavoriteUiState (
    val students: List<Student> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val isEmpty : Boolean = false,
    val sortOrder: String = "ASC",
)