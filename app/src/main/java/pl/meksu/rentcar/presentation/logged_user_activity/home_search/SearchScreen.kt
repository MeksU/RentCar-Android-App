package pl.meksu.rentcar.presentation.logged_user_activity.home_search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.components.OfferItem
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.components.TypeButton

@Composable
fun SearchScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onOfferClick: (Offer) -> Unit
) {
    val state = viewModel.state.value
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var selectedType by remember { mutableStateOf("") }

    val carTypes = listOf("Coupe", "Kombi", "Sedan", "SUV", "Hatchback", "Minivan", "VAN")

    val filteredOffers = state.offers.filter { offer ->
        val car = "${offer.car.brand} ${offer.car.model}"
        val searchQuery = searchText.text.trim().lowercase()

        (searchQuery.isEmpty() || car.lowercase().contains(searchQuery)) &&
                (offer.car.type.contains(selectedType))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Wpisz markę lub model...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(32.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TypeButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == "") MaterialTheme.colorScheme.primary else Color.Black
                    ),
                    onClick = { selectedType = "" },
                    text = "Wszystkie"
                )
            }
            items(carTypes) { type ->
                TypeButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == type) MaterialTheme.colorScheme.primary else Color.Black
                    ),
                    onClick = { selectedType = type },
                    text = type
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(filteredOffers) { offer ->
                OfferItem(offer) {
                    onOfferClick(offer)
                }
            }
        }

        if(state.error.isNotBlank()) {
            Text(state.error, color = MaterialTheme.colorScheme.error)
        }
        if(state.isLoading) {
            CircularProgressIndicator()
        }
    }
}