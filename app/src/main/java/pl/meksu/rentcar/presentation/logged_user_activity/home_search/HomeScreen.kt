package pl.meksu.rentcar.presentation.logged_user_activity.home_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.presentation.logged_user_activity.home_search.components.OfferItem

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onOfferClick: (Offer) -> Unit
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Cześć, ${viewModel.userName.value}!",
            modifier = Modifier.padding(8.dp).padding(top = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            text = "Sprawdź naszą promocyjną ofertę",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 20.sp
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(state.offers.filter { it.promotion > 0.0 }) { offer ->
                OfferItem(offer) {
                    onOfferClick(offer)
                }
            }
        }

        if(state.error.isNotBlank()) {
            Text(state.error, color = MaterialTheme.colorScheme.error)
        }
    }
}