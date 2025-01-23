package pl.meksu.rentcar.presentation.main_activity.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.main_activity.register.components.RegisterTextField
import pl.meksu.rentcar.presentation.ui.theme.Epilogue
import pl.meksu.rentcar.presentation.ui.theme.MainBlue
import pl.meksu.rentcar.presentation.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    val controller = LocalSoftwareKeyboardController.current

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
                        Icon(
                            modifier = Modifier.size(36.dp),
                            painter = painterResource(id = R.drawable.ic_car),
                            contentDescription = null,
                            tint = MainBlue
                        )
                        Text(
                            text = "RentCar",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.clearState()
                            navController.navigateUp()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back", tint = MainBlue)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
        ) {
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable(enabled = false) {}
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MainBlue
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
                Column(Modifier.padding(horizontal = 16.dp)) {
                    RegisterTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Imię"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = surname,
                        onValueChange = { surname = it },
                        label = "Nazwisko"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "E-mail"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Hasło",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = password2,
                        onValueChange = { password2 = it },
                        label = "Powtórz hasło",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = "Telefon",
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = "Adres",
                        imeAction = ImeAction.Next
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    RegisterTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = "Kod pocztowy",
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.register(
                                name,
                                surname,
                                email,
                                password,
                                password2,
                                phone,
                                address,
                                postalCode
                        )
                        controller?.hide()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainBlue
                    ),
                    enabled = !state.isLoading
                ) {
                    Text(
                        text = "Zarejestruj się",
                        fontFamily = Epilogue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            if (state.error.isNotBlank()) {
                message = state.error
            }

            if (state.message.isNotBlank()) {
                Toast.makeText(
                    LocalContext.current,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
                navController.navigateUp()
            }
        }
    }
}