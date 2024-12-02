package pl.meksu.rentcar.presentation.main_activity.reset_password

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import pl.meksu.rentcar.presentation.main_activity.reset_password.components.FirstTwoSteps
import pl.meksu.rentcar.presentation.ui.theme.Epilogue
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val email by viewModel.email
    val resetCode by viewModel.resetCode
    val password by viewModel.password
    val password2 by viewModel.password2
    val fragment by viewModel.fragment
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
                            fontSize = 28.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.goBack()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back", tint = MainBlue)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_lock_reset_24),
                        contentDescription = "Lock reset icon",
                        tint = MainBlue,
                        modifier = Modifier.size(164.dp)
                    )
                    Text(
                        text = "RESETOWANIE HASŁA",
                        fontSize = 36.sp,
                        color = MainBlue,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when (fragment) {
                        1 -> {
                            FirstTwoSteps(
                                mainText = "Podaj swój adres e-mail",
                                textValue = email,
                                onValueChange = {
                                    viewModel.updateEmail(it)
                                },
                                label = "E-mail",
                                onClick = {
                                    controller?.hide()
                                    viewModel.requestReset()
                                },
                                buttonText = "Wyślij kod",
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                )
                            )
                        }
                        2 -> {
                            FirstTwoSteps(
                                mainText = "Podaj 6-cyfrowy kod resetujący",
                                textValue = resetCode,
                                onValueChange = {
                                    viewModel.updateResetCode(it)
                                },
                                label = "Kod resetujący",
                                onClick = {
                                    controller?.hide()
                                    viewModel.verifyResetCode()
                                },
                                buttonText = "Sprawdź kod",
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    keyboardType = KeyboardType.Number
                                )
                            )
                        }
                        3 -> {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Utwórz swoje nowe hasło",
                                    fontSize = 20.sp,
                                )
                                Spacer(Modifier.height(16.dp))
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = {
                                        viewModel.updatePassword(it)
                                    },
                                    label = {
                                        Text(
                                            text = "Nowe hasło"
                                        )
                                    },
                                    shape = CircleShape,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                OutlinedTextField(
                                    value = password2,
                                    onValueChange = {
                                        viewModel.updateSecondPassword(it)
                                    },
                                    label = {
                                        Text(
                                            text = "Powtórz hasło"
                                        )
                                    },
                                    shape = CircleShape,
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = ImeAction.Done,
                                        keyboardType = KeyboardType.Password
                                    ),
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                Button(
                                    shape = CircleShape,
                                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                                    onClick = {
                                        viewModel.resetPassword()
                                    }
                                ) {
                                    Text(
                                        text = "Zresetuj hasło",
                                        fontFamily = Epilogue,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        else -> {
                            viewModel.goBack()
                            navController.popBackStack()
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
                if(state.error.isNotBlank()) {
                    message = state.error
                }
                if(state.message.isNotBlank()) {
                    Toast.makeText(
                        LocalContext.current,
                        state.message,
                        Toast.LENGTH_LONG
                    ).show()
                    message = ""
                    viewModel.clearState()
                }
            }
        }
    }
}