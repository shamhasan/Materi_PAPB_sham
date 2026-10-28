package ih.pam.tobearsitekturpapb

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ih.pam.tobearsitekturpapb.data.remote.RetrofitClient
import ih.pam.tobearsitekturpapb.data.remote.datasource.StudentRemoteDataSource
import ih.pam.tobearsitekturpapb.data.repository.DefaultStudentRepository
import ih.pam.tobearsitekturpapb.ui.detail.DetailViewModel
import ih.pam.tobearsitekturpapb.ui.home.HomeViewModel
import ih.pam.tobearsitekturpapb.ui.screens.DetailScreen
import ih.pam.tobearsitekturpapb.ui.screens.HomeScreen
import ih.pam.tobearsitekturpapb.ui.screens.ProfileScreen


@Composable
fun NavigationLabApp() {
    val remoteDataSource = remember { StudentRemoteDataSource(RetrofitClient.apiService) }
    val repository = remember { DefaultStudentRepository(remoteDataSource) }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val homeVM: HomeViewModel = viewModel(
                factory = HomeViewModel.factory(repository)
            )

            val homeUiState by homeVM.uiState.collectAsStateWithLifecycle()
            val searchQuery by homeVM.searchQuery.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { homeVM.fetchAllStudents() }

            HomeScreen(
                uiState = homeUiState,
                onStudentClick = {id-> navController.navigate(Routes.detail(id))},
                onRetry = homeVM::retry,
                searchQuery = searchQuery,
                onSearchQueryChange = homeVM::onSearchQueryChange
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val studentId =
                backStackEntry.arguments?.getInt("studentId") ?: 0

            val viewModelKita: DetailViewModel =
                viewModel(factory = DetailViewModel.factory(repository))

            val detailUiState by viewModelKita.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(studentId) {
                viewModelKita.loadStudent(studentId)
            }

            DetailScreen(
                onBack = { navController.popBackStack() },
                uiState = detailUiState
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}