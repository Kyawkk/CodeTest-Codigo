package com.kyawzinlinn.codetest_codigo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kyawzinlinn.codetest_codigo.screen.AllergyScreen
import com.kyawzinlinn.codetest_codigo.screen.DataViewModel
import com.kyawzinlinn.codetest_codigo.screen.DietScreen
import com.kyawzinlinn.codetest_codigo.screen.FinalScreen
import com.kyawzinlinn.codetest_codigo.screen.HealthConcernScreen
import com.kyawzinlinn.codetest_codigo.screen.SplashScreen

@Composable
fun NavigationGraph(
    viewModel: DataViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashScreen.name
    ) {
        composable(ScreenRoute.SplashScreen.name) {
            viewModel.apply {
                reset()
                updateProgress(0f)
                updateBottomProgressBarStatus(false)
            }
            SplashScreen(onGetStartClick = { navController.navigate(ScreenRoute.HealthConcernScreen.name) })
        }

        composable(ScreenRoute.HealthConcernScreen.name) {

            viewModel.apply {
                updateProgress(0.25f)
                updateBottomProgressBarStatus(true)
            }
            HealthConcernScreen(
                viewModel = viewModel,
                onBackButtonClick = {
                    navController.navigateUp()
                },
                onNextButtonClick = {
                    navController.navigate(ScreenRoute.DietScreen.name)
                }
            )
        }

        composable(ScreenRoute.DietScreen.name) {

            viewModel.apply {
                updateProgress(0.5f)
                updateBottomProgressBarStatus(true)
            }
            DietScreen(
                viewModel = viewModel,
                onBackButtonClick = {navController.navigateUp()},
                onNextButtonClick = {navController.navigate(ScreenRoute.AllergyScreen.name)}
            )
        }

        composable(ScreenRoute.AllergyScreen.name) {
            viewModel.apply {
                updateProgress(0.75f)
                updateBottomProgressBarStatus(true)
            }
            AllergyScreen(
                viewModel = viewModel,
                onBackButtonClick = {navController.navigateUp()},
                onNextButtonClick = {navController.navigate(ScreenRoute.FinalScreen.name)}
            )
        }

        composable(ScreenRoute.FinalScreen.name) {
            viewModel.apply {
                updateProgress(1f)
                updateBottomProgressBarStatus(true)
            }
            FinalScreen(viewModel = viewModel)
        }
    }
}