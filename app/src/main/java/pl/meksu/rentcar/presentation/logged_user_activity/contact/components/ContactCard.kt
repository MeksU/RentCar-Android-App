package pl.meksu.rentcar.presentation.logged_user_activity.contact.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@Composable
fun ContactCard(
    address: String,
    phoneNumber: String,
    email: String,
    hours: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ContactCardRow(
                icon = painterResource(R.drawable.baseline_location_pin_24),
                text = address
            )
            ContactCardRow(
                icon = painterResource(R.drawable.baseline_local_phone_24),
                text = phoneNumber
            )
            ContactCardRow(
                icon = painterResource(R.drawable.outline_email_24),
                text = email
            )
            ContactCardRow(
                icon = painterResource(R.drawable.baseline_access_time_24),
                text = hours
            )
        }
    }
}

@Composable
fun ContactCardRow(
    icon: Painter,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            painter = icon,
            tint = MainBlue,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
    }
}