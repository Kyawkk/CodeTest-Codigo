package com.kyawzinlinn.movie.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kyawzinlinn.movie.presentation.home_screen.HomeScreen
import com.kyawzinlinn.movie.presentation.home_screen.HomeViewModel
import com.kyawzinlinn.movie.presentation.movie_detail.DetailScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.name
    ) {
        composable(route = ScreenRoute.Home.name) {
            HomeScreen(homeViewModel, onMovieItemClick = {
                navController.navigate("${ScreenRoute.Detail.name}/$it")
            })
        }

        composable(route = "${ScreenRoute.Detail.name}/{movieId}") {

            val movieId = it.arguments?.getString("movieId")
            homeViewModel.getMovieDetails(movieId ?: "")
            DetailScreen(viewModel = homeViewModel, navigateBack = {
                navController.navigateUp()
            })
        }
    }
}