package pl.meksu.rentcar.presentation.main_activity.login

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pl.meksu.rentcar.R
import pl.meksu.rentcar.presentation.main_activity.Screen
import pl.meksu.rentcar.presentation.logged_user_activity.LoggedUserActivity
import pl.meksu.rentcar.presentation.ui.theme.DarkBlue
import pl.meksu.rentcar.presentation.ui.theme.Epilogue
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val email by viewModel.email
    val password by viewModel.password
    var message by remember { mutableStateOf("") }

    val controller = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        if(state.isLoading) {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_car),
                    contentDescription = "he",
                    tint = MainBlue,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = "RentCar",
                    color = MainBlue,
                    fontFamily = Epilogue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 64.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { viewModel.updateEmail(it) },
                            label = { Text("E-mail") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = CircleShape,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { viewModel.updatePassword(it) },
                            label = { Text("Hasło") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            shape = CircleShape
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                viewModel.login()
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
                                text = "Zaloguj",
                                fontFamily = Epilogue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                navController.navigate(Screen.RegisterScreen.route)
                                viewModel.clearState()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkBlue
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
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Zapomniałeś hasła?",
                            fontFamily = FontFamily.Default,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.ResetPasswordScreen.route)
                                viewModel.clearState()
                            }
                        )
                    }
                }
                if (state.isLoggedIn) {
                    val intent = Intent(context, LoggedUserActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)

                    Toast.makeText(
                        context,
                        "Cześć ${state.loginResponse?.userName ?: ""}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.clearState()
                }
                if (state.error.isNotBlank()) {
                    message = state.error
                }
            }
        }
    }
}