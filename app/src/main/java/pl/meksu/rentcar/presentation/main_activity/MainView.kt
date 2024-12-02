package pl.meksu.rentcar.presentation.main_activity

import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.meksu.rentcar.presentation.logged_user_activity.LoggedUserActivity
import pl.meksu.rentcar.presentation.main_activity.car_list.CarListScreen
import pl.meksu.rentcar.presentation.main_activity.login.LoginScreen
import pl.meksu.rentcar.presentation.main_activity.register.RegisterScreen
import pl.meksu.rentcar.presentation.main_activity.reset_password.ResetPasswordScreen

@Composable
fun MainView(
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.tryToLogin()
    }

    LaunchedEffect(viewModel.loggedIn.value) {
        if(viewModel.loggedIn.value) {
            Toast.makeText(
                context,
                "Zalogowano",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(context, LoggedUserActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }
    NavHost(
        navController = navController,
        startDestination = Screen.CarListScreen.route
    ) {
        composable(
            route = Screen.CarListScreen.route
        ) {
            CarListScreen(navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController)
        }
        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(navController)
        }
        composable(
            route = Screen.ResetPasswordScreen.route
        ) {
            ResetPasswordScreen(navController)
        }
    }
}