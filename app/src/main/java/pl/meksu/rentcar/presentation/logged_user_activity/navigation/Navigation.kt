package pl.meksu.rentcar.presentation.logged_user_activity.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
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

        }
        composable(Screen.DrawerScreen.Account.route) {

        }
        composable(Screen.DrawerScreen.Reviews.route) {

        }
        composable(
            route = Screen.BottomScreen.Reservations.route,
            enterTransition = { expandHorizontally() },
            popEnterTransition = { expandHorizontally() },
            exitTransition = { shrinkHorizontally() },
            popExitTransition = { shrinkHorizontally() }
        ) {
            ReservationsScreen(onPayClick = { id, price ->
                onPayClick(id, price)
            })
        }
        composable(
            route = Screen.BottomScreen.Home.route,
            enterTransition = { expandHorizontally() },
            popEnterTransition = { expandHorizontally() },
            exitTransition = { shrinkHorizontally() },
            popExitTransition = { shrinkHorizontally() }
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            HomeScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(
            route = Screen.BottomScreen.Search.route,
            enterTransition = { expandHorizontally() },
            popEnterTransition = { expandHorizontally() },
            exitTransition = { shrinkHorizontally() },
            popExitTransition = { shrinkHorizontally() }
        ) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            SearchScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(
            route = Screen.Detail.route,
            enterTransition = { expandHorizontally() },
            popEnterTransition = { expandHorizontally() },
            exitTransition = { shrinkHorizontally() },
            popExitTransition = { shrinkHorizontally() }
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