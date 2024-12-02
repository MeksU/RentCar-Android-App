package pl.meksu.rentcar.presentation.logged_user_activity

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.logged_user_activity.navigation.Navigation
import pl.meksu.rentcar.presentation.logged_user_activity.navigation.Screen
import pl.meksu.rentcar.presentation.logged_user_activity.navigation.screensInBottom
import pl.meksu.rentcar.presentation.logged_user_activity.navigation.screensInDrawer
import pl.meksu.rentcar.presentation.main_activity.MainActivity
import pl.meksu.rentcar.presentation.ui.theme.MainBlue
import pl.meksu.rentcar.presentation.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoggedUserView(
    onPayClick: (Int, String) -> Unit,
    viewModel: LoggedUserViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val navController: NavController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = rememberSaveable(currentRoute) {
        when (currentRoute) {
            Screen.BottomScreen.Home.route -> Screen.BottomScreen.Home.title
            Screen.BottomScreen.Search.route -> Screen.BottomScreen.Search.title
            Screen.BottomScreen.Reservations.route -> Screen.BottomScreen.Reservations.title
            Screen.DrawerScreen.Reviews.route -> Screen.DrawerScreen.Reviews.title
            Screen.DrawerScreen.Contact.route -> Screen.DrawerScreen.Contact.title
            Screen.DrawerScreen.Account.route -> Screen.DrawerScreen.Account.title
            Screen.Detail.route -> Screen.Detail.title
            else -> "RentCar"
        }
    }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val navigateToReservations by viewModel.navigateToReservations

    if (navigateToReservations) {
        navController.navigate(Screen.BottomScreen.Reservations.route) {
            popUpTo(Screen.BottomScreen.Home.route) { inclusive = true }
        }
        viewModel.resetNavigation()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.ic_car),
                        contentDescription = null,
                        tint = MainBlue
                    )
                    Text(
                        text = "RentCar",
                        maxLines = 1,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 28.sp,
                        color = MainBlue,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                LazyColumn(Modifier.padding(16.dp)) {
                    items(screensInDrawer) { item ->
                        DrawerItem(selected = currentRoute == item.dRoute, item = item) {
                            scope.launch {
                                drawerState.close()
                            }
                            navController.navigate(item.dRoute)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showLogoutDialog = true
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_logout_24),
                                contentDescription = "Wyloguj",
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    top = 20.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                ),
                                tint = Color.Black
                            )
                            Text(
                                text = "Wyloguj",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = MainBlue,
                    ),
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = title,
                                maxLines = 1,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = fontFamily,
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        }
                    },
                    navigationIcon = {
                        if(currentRoute != Screen.Detail.route) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_menu_24),
                                    contentDescription = "Menu",
                                    tint = MainBlue
                                )
                            }
                        }
                        else {
                            IconButton(
                                onClick = {
                                    navController.popBackStack()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Go back",
                                    tint = MainBlue
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                if(currentRoute != Screen.Detail.route) {
                    NavigationBar(
                        modifier = Modifier.wrapContentSize(),
                        containerColor = MainBlue,
                        contentColor = Color.White
                    ) {
                        screensInBottom.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.bRoute,
                                onClick = {
                                    navController.navigate(item.bRoute)
                                },
                                icon = {
                                    Icon(
                                        painter = if (currentRoute == item.bRoute) {
                                            painterResource(item.fIcon)
                                        } else {
                                            painterResource(item.oIcon)
                                        },
                                        contentDescription = item.bTitle,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        fontWeight = if (currentRoute == item.bRoute) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Light
                                        }
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedTextColor = Color.White,
                                    unselectedTextColor = Color.White
                                )
                            )
                        }
                    }
                }
            }
        ) {
            Navigation(navController = navController, pd = it) { id, price ->
                onPayClick(id, price)
            }
        }
    }
    if(showLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            },
            title = {
                Text(text = "Potwierdzenie wylogowania")
            },
            text = {
                Text(text = "Czy na pewno chcesz się wylogować?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.logOut()
                        showLogoutDialog = false

                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Wyloguj")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                    }
                ) {
                    Text("Anuluj")
                }
            }
        )
    }
}

@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val color = if (selected) MainBlue else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onDrawerItemClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            modifier = Modifier.padding(
                start = 8.dp,
                top = 20.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            tint = color
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = fontFamily,
            modifier = Modifier.padding(vertical = 16.dp),
            color = color
        )
    }
}