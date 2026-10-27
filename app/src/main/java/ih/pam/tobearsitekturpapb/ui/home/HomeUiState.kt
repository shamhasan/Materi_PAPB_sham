package ih.pam.tobearsitekturpapb.ui.home

import ih.pam.tobearsitekturpapb.data.model.Student

data class HomeUiState (
    val students: List<Student>? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)