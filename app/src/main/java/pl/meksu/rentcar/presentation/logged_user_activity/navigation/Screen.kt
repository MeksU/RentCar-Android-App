package pl.meksu.rentcar.presentation.logged_user_activity.navigation

import androidx.annotation.DrawableRes
import pl.meksu.rentcar.R

sealed class Screen(val title: String, val route: String) {

    sealed class DrawerScreen(
        val dTitle: String, val dRoute: String, @DrawableRes val icon: Int
    ): Screen(dTitle, dRoute) {
        data object Account: DrawerScreen(
            "Moje konto",
            "account",
            R.drawable.baseline_account_box_24
        )
        data object Contact: DrawerScreen(
            "Kontakt",
            "contact",
            R.drawable.baseline_email_24
        )
        data object Reviews: DrawerScreen(
            "Opinie",
            "reviews",
            R.drawable.baseline_rate_review_24
        )
    }

    sealed class BottomScreen(
        val bTitle: String,
        val bRoute: String,
        @DrawableRes val oIcon: Int,
        @DrawableRes val fIcon: Int,
        val label: String
    ): Screen(bTitle, bRoute) {
        data object Home: BottomScreen(
            "RentCar",
            "home",
            R.drawable.icons8_home_outlined,
            R.drawable.icons8_home_filled,
            "Strona główna"
        )
        data object Search: BottomScreen(
            "Szukaj",
            "search",
            R.drawable.icons8_search_outlined,
            R.drawable.icons8_search_filled,
            "Szukaj"
        )
        data object Reservations: BottomScreen(
            "Twoje rezerwacje",
            "reservations",
            R.drawable.icons8_calendar_outlined,
            R.drawable.icons8_calendar_filled,
            "Rezerwacje"
        )
    }

    data object Detail: Screen("Oferta", "detail")
}

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Contact,
    Screen.DrawerScreen.Reviews
)

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Search,
    Screen.BottomScreen.Reservations
)