package pl.meksu.rentcar.presentation.logged_user_activity.reservations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import coil.compose.rememberAsyncImagePainter
import pl.meksu.rentcar.R
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.domain.model.UserReservation
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReservationItem(
    reservation: UserReservation,
    onPayClick: (Int, String) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    val imageUrl = Constants.IMAGE_URL + reservation.offer.car.image
    val car = reservation.offer.car
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("pl", "PL"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .shadow(3.dp, shape = RoundedCornerShape(16.dp))
            .background(color = Color.White, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Car Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentScale = ContentScale.Fit
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            Modifier.fillMaxHeight().weight(1f)
                        ) {
                            Text(
                                text = "${car.brand} ${car.model}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "${car.engine}, ${car.power}km, ${car.fuelType}",
                                fontWeight = FontWeight.Bold,
                                color = Color.LightGray,
                                fontSize = 10.sp
                            )
                        }
                        Column(
                            Modifier.fillMaxSize().weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_event_available_24),
                                    contentDescription = "Start"
                                )
                                Text(
                                    text = reservation.startDate.format(formatter),
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            }
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_event_busy_24),
                                    contentDescription = "Start"
                                )
                                Text(
                                    text = reservation.endDate.format(formatter),
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (reservation.paymentStatus == "Niezapłacona") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Button(
                                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp, top = 4.dp),
                                onClick = {
                                    onPayClick(
                                        reservation.id,
                                        reservation.price.toString()
                                    )
                                },
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = "Zapłać teraz".uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Row(Modifier.padding(end = 8.dp)) {
                                Text("Cena: ")
                                Text(
                                    text = "${reservation.price.fastRoundToInt()}zł",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color(0xff00c04b)
                                )
                            }
                        }

                    } else {
                        Text(
                            text = "Rezerwacja opłacona",
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
            if(reservation.startDate > LocalDate.now()) {
                IconButton(
                    onClick = {
                        onDeleteClick(reservation.id)
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_clear_24),
                        contentDescription = "Delete reservation",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}
