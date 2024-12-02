package pl.meksu.rentcar.presentation.main_activity.reset_password.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.meksu.rentcar.presentation.ui.theme.Epilogue

@Composable
fun FirstTwoSteps(
    mainText: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    label: String,
    onClick: () -> Unit,
    buttonText: String,
    keyboardOptions: KeyboardOptions
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mainText,
            fontSize = 20.sp,
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = textValue,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = label
                )
            },
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            keyboardOptions = keyboardOptions
        )
        Button(
            shape = CircleShape,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            onClick = {
                onClick()
            }
        ) {
            Text(
                text = buttonText,
                fontFamily = Epilogue,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}