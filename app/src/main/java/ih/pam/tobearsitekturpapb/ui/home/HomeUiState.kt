package ih.pam.tobearsitekturpapb.ui.home

import androidx.compose.foundation.text.input.TextFieldState
import ih.pam.tobearsitekturpapb.data.model.Student

data class HomeUiState (
    val students: List<Student> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
    val searchQuery: TextFieldState = TextFieldState(),
    val isEmpty : Boolean = false,
)