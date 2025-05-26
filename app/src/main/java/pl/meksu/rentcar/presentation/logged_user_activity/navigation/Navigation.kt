package pl.meksu.rentcar.presentation.logged_user_activity.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.meksu.rentcar.domain.model.Car
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.presentation.logged_user_activity.contact.ContactScreen
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.HomeScreen
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.SearchScreen
import pl.meksu.rentcar.presentation.logged_user_activity.offer_detail.OfferDetailScreen
import pl.meksu.rentcar.presentation.logged_user_activity.reservations.ReservationsScreen

@Composable
fun Navigation(
    navController: NavController,
    pd: PaddingValues,
    onPayClick: (Int, String) -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.BottomScreen.Home.route,
        modifier = Modifier.padding(pd)
    ) {
        composable(Screen.DrawerScreen.Contact.route) {
            ContactScreen()
        }
        composable(Screen.DrawerScreen.Account.route) {

        }
        composable(Screen.DrawerScreen.Reviews.route) {

        }
        composable(
            route = Screen.BottomScreen.Reservations.route,
            exitTransition = { fadeOut(animationSpec = tween(750)) },
            enterTransition = { fadeIn(animationSpec = tween(750)) },
            popExitTransition = { fadeOut(animationSpec = tween(750)) },
            popEnterTransition = { fadeIn(animationSpec = tween(750)) }
        ) {
            ReservationsScreen(onPayClick = { id, price ->
                onPayClick(id, price)
            })
        }
        composable(
            route = Screen.BottomScreen.Home.route,
            exitTransition = { fadeOut(animationSpec = tween(750)) },
            enterTransition = { fadeIn(animationSpec = tween(750)) },
            popExitTransition = { fadeOut(animationSpec = tween(750)) },
            popEnterTransition = { fadeIn(animationSpec = tween(750)) }
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            HomeScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(
            route = Screen.BottomScreen.Search.route,
            exitTransition = { fadeOut(animationSpec = tween(750)) },
            enterTransition = { fadeIn(animationSpec = tween(750)) },
            popExitTransition = { fadeOut(animationSpec = tween(750)) },
            popEnterTransition = { fadeIn(animationSpec = tween(750)) }
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            SearchScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(
            route = Screen.Detail.route,
            exitTransition = { fadeOut(animationSpec = tween(750)) },
            enterTransition = { fadeIn(animationSpec = tween(750)) },
            popExitTransition = { fadeOut(animationSpec = tween(750)) },
            popEnterTransition = { fadeIn(animationSpec = tween(750)) }
        ) {
            val offer = navController.previousBackStackEntry?.savedStateHandle?.get<Offer>("offer")
                ?: Offer(id = 0, price = 0.0, description = "", promotion = 0.0, car = Car(
                            id = 0, image = "", model = "", brand = "", power = 0, seats = 0,
                            type = "", engine = "", fuelType = "", transmission = ""))

            OfferDetailScreen(offer) {
                navController.navigate(Screen.BottomScreen.Reservations.route)
            }
        }
    }
}