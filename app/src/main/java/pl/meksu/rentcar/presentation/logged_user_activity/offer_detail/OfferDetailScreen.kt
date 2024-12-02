package pl.meksu.rentcar.presentation.logged_user_activity.offer_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.meksu.rentcar.R
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.presentation.ui.theme.MainBlue
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfferDetailScreen(
    offer: Offer,
    viewModel: OfferDetailViewModel = hiltViewModel(),
    goToReservations: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchReservations(offer.id)
    }

    val state = viewModel.state.value
    val createState = viewModel.createState.value
    val imageUrl = Constants.IMAGE_URL + offer.car.image
    val useCaseState = rememberUseCaseState()
    var startDate by remember { mutableStateOf(LocalDate.now().minusWeeks(1)) }
    var endDate by remember { mutableStateOf(LocalDate.now().minusWeeks(1)) }
    var disabledDates by remember { mutableStateOf<List<LocalDate>>(emptyList()) }
    val context = LocalContext.current

    LaunchedEffect(state) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = mutableListOf<LocalDate>()

            state.reservations.forEach {
                val numberOfDays = ChronoUnit.DAYS.between(it.startDate, it.endDate)

                for(i in 0..numberOfDays) {
                    list.add(it.startDate.plusDays(i))
                }
            }
            disabledDates = list
        }
    }

    if(state.isLoading || createState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Gray)
                .alpha(0.1f),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }

    CalendarDialog(
        state = useCaseState,
        selection = CalendarSelection.Period { start, end ->
            startDate = start
            endDate = end
        },
        config = CalendarConfig(
            boundary = (LocalDate.now()..LocalDate.now().plusMonths(2)),
            disabledDates = disabledDates
        )
    )

    Column (Modifier.padding(bottom = 16.dp, end = 16.dp, start = 16.dp).fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Car Image",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "${offer.car.brand.uppercase()} ${offer.car.model.uppercase()}",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))

        LazyColumn(
            Modifier.fillMaxSize().weight(1f)
        ) {
            item {
                Text(
                    text = "O ofercie:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(offer.description)
                if (offer.promotion > 0.0) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Promocyjna oferta",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .shadow(3.dp, shape = RoundedCornerShape(16.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_monetization_on_24),
                            contentDescription = "Coin icon",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "  ",
                            fontSize = 20.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (offer.promotion > 0.0) {
                                Text(
                                    text = "${offer.promotion}zł",
                                    fontSize = 16.sp,
                                    color = Color.Red,
                                    style = TextStyle(
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                )
                                Text("  ")
                            }
                            Text(
                                text = "${offer.price.fastRoundToInt()}zł",
                                fontSize = 20.sp,
                                color = MainBlue,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "/dzień",
                                fontSize = 16.sp
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_engine),
                            contentDescription = "Engine icon",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "   ${offer.car.engine}, ${offer.car.fuelType}",
                            fontSize = 20.sp
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_car_wheel),
                            contentDescription = "Wheel icon",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                        val transmission =
                            if (offer.car.transmission == "automatyczna") "automat" else "manual"
                        Text(
                            text = "   ${offer.car.power}km, $transmission",
                            fontSize = 20.sp
                        )
                    }
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_car_seat),
                            contentDescription = "Seat icon",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                        val seats = when (offer.car.seats) {
                            1 -> "miejsce"
                            in 2..4 -> "miejsca"
                            else -> "miejsc"
                        }
                        Text(
                            text = "  ${offer.car.seats} $seats",
                            fontSize = 20.sp
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.End)
        ) {
            if(startDate >= LocalDate.now()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icons8_calendar_filled),
                        contentDescription = "Calendar icon",
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "  $startDate - $endDate",
                        fontSize = 20.sp
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                onClick = {
                    useCaseState.show()
                }
            ) {
                Text(
                    text = "Wybierz termin",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp, end = 8.dp, start = 8.dp),
                onClick = {
                    if(startDate >= LocalDate.now()) {
                        viewModel.createReservation(startDate, endDate, offer.id)
                    } else {
                        Toast.makeText(
                            context, "Proszę wybrać datę.", Toast.LENGTH_LONG
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "ZAREZERWUJ",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }

    if(createState.success.isNotBlank()) {
        Toast.makeText(
            context, createState.success, Toast.LENGTH_LONG
        ).show()
        goToReservations()
        viewModel.clearCreateState()
    }

    if(createState.error.isNotBlank()) {
        Toast.makeText(
            context, createState.error, Toast.LENGTH_LONG
        ).show()
        viewModel.clearCreateState()
    }
}