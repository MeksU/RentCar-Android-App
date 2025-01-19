package pl.meksu.rentcar.presentation.logged_user_activity.reservations

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.components.TypeButton
import pl.meksu.rentcar.presentation.logged_user_activity.reservations.components.ReservationItem
import java.time.LocalDate

@Composable
fun ReservationsScreen(
    viewModel: ReservationsViewModel = hiltViewModel(),
    onPayClick: (Int, String) -> Unit
) {
    val state = viewModel.state.value
    val deleteState = viewModel.deleteState.value
    val context = LocalContext.current
    var deleteOfferIdState by rememberSaveable { mutableIntStateOf(-2) }

    val reservationFilters = listOf("Aktywne", "Zakończone", "Niezapłacone", "Wszystkie")
    var reservationFilter by remember { mutableStateOf("Aktywne") }

    val filteredReservations = state.reservations.filter { reservation ->
        when(reservationFilter) {
            "Aktywne" -> reservation.endDate >= LocalDate.now()
            "Zakończone" -> reservation.endDate < LocalDate.now()
            "Niezapłacone" -> reservation.paymentStatus == "Niezapłacona"
            else -> reservation.id >= 0
        }
    }.sortedBy { it.startDate }

    if(state.isLoading || deleteState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reservationFilters) { type ->
                TypeButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (reservationFilter == type) MaterialTheme.colorScheme.primary else Color.Black
                    ),
                    onClick = { reservationFilter = type },
                    text = type
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(filteredReservations) { reservation ->
                ReservationItem(
                    reservation,
                    onPayClick = { id, price ->
                        onPayClick(id, price)

                    },
                    onDeleteClick = {
                        deleteOfferIdState = it
                    }
                )
            }
        }
    }

    if(deleteOfferIdState > 0) {
        AlertDialog(
            onDismissRequest = {
                deleteOfferIdState = -2
            },
            title = {
                Text(text = "Potwierdzenie odwołania rezerwacji")
            },
            text = {
                Text(text = "Czy na pewno chcesz odwołać rezerwację?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteReservation(deleteOfferIdState)
                        deleteOfferIdState = -2
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("TAK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        deleteOfferIdState = -2
                    }
                ) {
                    Text("NIE")
                }
            }
        )
    }

    if(deleteState.error.isNotBlank()) {
        Toast.makeText(
            context, deleteState.error, Toast.LENGTH_LONG
        ).show()
        viewModel.clearDeleteState()
    }
}