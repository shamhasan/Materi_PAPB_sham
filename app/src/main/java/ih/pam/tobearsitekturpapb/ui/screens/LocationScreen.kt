package ih.pam.tobearsitekturpapb.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import ih.pam.tobearsitekturpapb.ui.location.LocationUiState
import ih.pam.tobearsitekturpapb.ui.location.LocationViewModel

@Composable
fun LocationScreen(viewModel: LocationViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val coarseGranted = result[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val fineGranted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true
        if (coarseGranted || fineGranted) {
            viewModel.refreshLocation()
        }
    }
    val hasLocationPermission = remember {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }
    Column(Modifier.padding(24.dp)) {
        Button(onClick = {
            if (hasLocationPermission) {
                viewModel.refreshLocation()
            } else {
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
            }
        }) { Text("Ambil Lokasi Saya") }
        when (val state = uiState) {
            LocationUiState.Idle -> Text("Lokasi belum diminta")
            LocationUiState.Loading -> CircularProgressIndicator()
            is LocationUiState.Success -> {
                Text("Latitude: ${state.location.latitude}")
                Text("Longitude: ${state.location.longitude}")
                Text("Accuracy: ${state.location.accuracy ?: 0f} m")
            }
            is LocationUiState.Error -> Text(state.message)
        }
    }
}
