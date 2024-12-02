package pl.meksu.rentcar.presentation.main_activity

sealed class Screen(val route: String) {
    data object CarListScreen: Screen("car_list_screen")
    data object LoginScreen: Screen("login_screen")
    data object RegisterScreen: Screen("register_screen")
    data object ResetPasswordScreen: Screen("reset_password_screen")
}