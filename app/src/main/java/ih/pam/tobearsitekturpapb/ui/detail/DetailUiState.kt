package ih.pam.tobearsitekturpapb.ui.detail

import ih.pam.tobearsitekturpapb.data.model.Student

data class DetailUiState(
    val student: Student? = null,
    val isLoading: Boolean = false,
    val errorMsg: String? = null
)
