package pl.meksu.rentcar.presentation.logged_user_activity.navigation

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
        composable(Screen.BottomScreen.Reservations.route) {
            ReservationsScreen(onPayClick = { id, price ->
                onPayClick(id, price)
            })
        }
        composable(Screen.BottomScreen.Home.route) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            HomeScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(Screen.BottomScreen.Search.route) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Offer>("offer")
            SearchScreen {
                navController.currentBackStackEntry?.savedStateHandle?.set("offer", it)
                navController.navigate(Screen.Detail.route)
            }
        }
        composable(Screen.Detail.route) {
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