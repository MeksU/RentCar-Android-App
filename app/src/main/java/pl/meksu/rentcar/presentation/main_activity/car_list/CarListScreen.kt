package pl.meksu.rentcar.presentation.main_activity.car_list

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.main_activity.Screen
import pl.meksu.rentcar.presentation.main_activity.car_list.components.CarListItem
import pl.meksu.rentcar.presentation.main_activity.car_list.components.CustomBottomRoundedShape
import pl.meksu.rentcar.presentation.ui.theme.Epilogue
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(
    navController: NavController,
    viewModel: CarListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val lazyListState = rememberLazyListState()
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MainBlue,
                    titleContentColor = Color.White,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(36.dp),
                            painter = painterResource(id = R.drawable.ic_car),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = "RentCar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.LoginScreen.route)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainBlue,
                            contentColor = Color.White
                        ),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "ZACZNIJ TERAZ",
                            fontFamily = Epilogue,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 4.dp),
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    Modifier.fillMaxWidth()
                        .background(
                            color = MainBlue,
                            shape = CustomBottomRoundedShape()
                        )
                ) {
                    Text(
                        text = "Wynajmij samochód dopasowany do Twoich potrzeb.\n" +
                                "Szeroka oferta modeli w najlepszych cenach!",
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier.padding(
                            top = 32.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 64.dp
                        ),
                        fontStyle = FontStyle.Italic
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = Color.Transparent)
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        state = lazyListState,
                        flingBehavior = snapBehavior
                    ) {

                        items(state.cars) { car ->
                            Box(modifier = Modifier.fillParentMaxWidth()) {
                                CarListItem(car)
                            }
                        }
                    }
                    if(state.cars.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, bottom = 8.dp)
                        ) {
                            val maxDots = 7
                            val currentIndex by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
                            val totalItems = state.cars.size

                            val startIndex = when {
                                totalItems <= maxDots -> 0
                                currentIndex <= maxDots / 2 -> 0 // Na początku listy
                                currentIndex >= totalItems - (maxDots / 2) -> totalItems - maxDots
                                else -> currentIndex - (maxDots / 2)
                            }
                            val endIndex = (startIndex + maxDots).coerceAtMost(totalItems)

                            (startIndex until endIndex).forEach { index ->
                                Box(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .size(8.dp)
                                        .background(
                                            color = if (index == currentIndex) Color.DarkGray else Color.LightGray,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                        Text(
                            text = "Zobacz naszą flotę – przewijaj zdjęcia i znajdź samochód dla siebie!",
                            modifier = Modifier.padding(horizontal = 24.dp),
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}