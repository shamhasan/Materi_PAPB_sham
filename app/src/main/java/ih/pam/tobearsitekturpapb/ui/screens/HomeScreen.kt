package ih.pam.tobearsitekturpapb.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ih.pam.tobearsitekturpapb.ui.home.HomeUiState


@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onStudentClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onSearchClick : (String) -> Unit
) {
    Scaffold() { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row {
                TextField(
                    state = uiState.searchQuery
                )
                Button(onClick = {
                    val querySearched = uiState.searchQuery.text.toString()
                    onSearchClick(querySearched)
                    Log.d("SHAMMMMM", "HomeScreen: ${querySearched} ")
                }
                ) {
                    Text("Search")
                }
            }
            when {
                uiState.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMsg != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(uiState.errorMsg)
                        Button(onClick = onRetry) { Text("Coba Lagi") }
                    }
                }
                uiState.isEmpty -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Data Kosong")
                    }
                }

                else -> {
                    LazyColumn {
                        items(uiState.students) { student ->
                            TextButton(onClick = { onStudentClick(student.id) }) {
                                Text("${student.id} - ${student.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}