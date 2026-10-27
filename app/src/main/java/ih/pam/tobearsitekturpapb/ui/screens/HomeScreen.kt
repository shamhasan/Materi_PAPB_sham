package ih.pam.tobearsitekturpapb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ih.pam.tobearsitekturpapb.data.model.Student
import ih.pam.tobearsitekturpapb.ui.home.HomeUiState
import ih.pam.tobearsitekturpapb.ui.home.HomeViewModel


@Composable
fun HomeScreen(
    onOpenDetail: (Int) -> Unit,
    onOpenProfile: () -> Unit,
    uiState: HomeUiState,
) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text("Home", style = MaterialTheme.typography.headlineMedium)
//        Button(onClick = { onOpenDetail(2027) }) {
//            Text("Buka Detail Mahasiswa 101")
//        }
//        OutlinedButton(onClick = onOpenProfile) {
//            Text("Buka Profile")
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row {
            Text("Home", style = MaterialTheme.typography.headlineMedium)
//            Button(onClick = uiState ) { }
        }
        var list = uiState.students
        Column() {
            list?.forEach { student ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                        .clickable(onClick = { onOpenDetail(student.id) })
                ) {
                    Column(Modifier.padding(8.dp).fillMaxWidth()) {
                        Text(student.id.toString())
                        Text(student.name)
                        Text(student.year.toString())
                    }
                }
            }
        }
    }
}