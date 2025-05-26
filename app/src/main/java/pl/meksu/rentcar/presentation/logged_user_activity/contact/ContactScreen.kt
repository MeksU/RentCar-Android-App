package pl.meksu.rentcar.presentation.logged_user_activity.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.logged_user_activity.contact.components.ContactCard
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@Composable
fun ContactScreen(
    viewModel: ContactViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ContactCard(
                address = "ul. Krakowska 12, Kraków 30-399",
                phoneNumber = "+48 567 576 675",
                email = "kontakt@rentcar.pl",
                hours = "Pon-Pt 9:00-19:00"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Formularz kontaktowy",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = { viewModel.updateTitle(it) },
                label = {
                    Text(
                        text = "Tytuł wiadomości"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = viewModel.message.value,
                onValueChange = { viewModel.updateMessage(it) },
                label = {
                    Text(
                        text = "Treść wiadomości"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                minLines = 6
            )

            Button(
                onClick = { viewModel.sendMessage() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "WYŚLIJ WIADOMOŚĆ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}