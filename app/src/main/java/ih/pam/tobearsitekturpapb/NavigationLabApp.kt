package ih.pam.tobearsitekturpapb

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.LocationServices
import ih.pam.tobearsitekturpapb.data.local.DatabaseProvider
import ih.pam.tobearsitekturpapb.data.location.DefaultLocationRepository
import ih.pam.tobearsitekturpapb.data.preferences.UserPreferencesRepository
import ih.pam.tobearsitekturpapb.data.remote.RetrofitClient
import ih.pam.tobearsitekturpapb.data.remote.datasource.StudentRemoteDataSource
import ih.pam.tobearsitekturpapb.data.repository.DefaultStudentRepository
import ih.pam.tobearsitekturpapb.data.repository.FavoriteRepository
import ih.pam.tobearsitekturpapb.ui.detail.DetailViewModel
import ih.pam.tobearsitekturpapb.ui.favorites.FavoriteViewModel
import ih.pam.tobearsitekturpapb.ui.home.HomeViewModel
import ih.pam.tobearsitekturpapb.ui.location.LocationViewModel
import ih.pam.tobearsitekturpapb.ui.screens.DetailScreen
import ih.pam.tobearsitekturpapb.ui.screens.FavoriteScreen
import ih.pam.tobearsitekturpapb.ui.screens.HomeScreen
import ih.pam.tobearsitekturpapb.ui.screens.LocationScreen
import ih.pam.tobearsitekturpapb.ui.screens.ProfileScreen
import kotlinx.coroutines.flow.collect


@Composable
fun NavigationLabApp() {
    val remoteDataSource = remember { StudentRemoteDataSource(RetrofitClient.apiService) }
    val repository = remember { DefaultStudentRepository(remoteDataSource) }
    val context = LocalContext.current
    val preferencesRepository = remember {
        UserPreferencesRepository(context.applicationContext)
    }
    val favoriteRepository = remember {
        FavoriteRepository(
            DatabaseProvider.getDatabase(
                context
            ).favoriteStudentDao()
        )
    }
    val locationRepository = remember {
        val appContext = context.applicationContext
        val locationClient = LocationServices.getFusedLocationProviderClient(appContext)
        DefaultLocationRepository(
            context = appContext,
            client = locationClient
        )
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val homeVM: HomeViewModel = viewModel(
                factory = HomeViewModel.factory(repository, preferencesRepository)
            )

            val homeUiState by homeVM.uiState.collectAsStateWithLifecycle()
            val searchQuery by homeVM.searchQuery.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) { homeVM.fetchAllStudents() }

            HomeScreen(
                uiState = homeUiState,
                onStudentClick = { id -> navController.navigate(Routes.detail(id)) },
                onRetry = homeVM::retry,
                searchQuery = searchQuery,
                onSearchQueryChange = homeVM::onSearchQueryChange,
                onRefresh = homeVM::refresh,
                onFavorite = { navController.navigate(Routes.FAVORITES) },
                onToggleLocation = { navController.navigate(Routes.LOCATION) }
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
                viewModel(factory = DetailViewModel.factory(repository, favoriteRepository))

            val detailUiState by viewModelKita.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(studentId) {
                viewModelKita.loadStudent(studentId)
            }

            DetailScreen(
                onBack = { navController.popBackStack() },
                uiState = detailUiState,
                toggleFavorite = viewModelKita::toggleFavorite
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.FAVORITES) {
            val favoriteVM: FavoriteViewModel = viewModel(
                factory = FavoriteViewModel.factory(favoriteRepository, preferencesRepository)
            )
            val favoriteUiState by favoriteVM.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                favoriteVM.fetchAllFavorites()
            }
            FavoriteScreen(
                uiState = favoriteUiState,
                onRetry = favoriteVM::fetchAllFavorites,
                onRefresh = favoriteVM::fetchAllFavorites,
                onBack = { navController.popBackStack() },
                onStudentClick = { id -> navController.navigate(Routes.detail(id)) },
                onSort = favoriteVM::changeSortOrder
            )
        }
        composable(Routes.LOCATION) {
            val locationVM: LocationViewModel = viewModel(
                factory = LocationViewModel.factory(locationRepository)
            )

            LocationScreen(
                viewModel = locationVM
            )


        }
    }
}