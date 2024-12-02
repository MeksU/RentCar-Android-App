package pl.meksu.rentcar.presentation.logged_user_activity.home_search.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TypeButton(
    colors: ButtonColors,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.height(40.dp),
        shape = RoundedCornerShape(20.dp),
        colors = colors
    ) {
        Text(
            text = text,
            fontSize = 14.sp
        )
    }
}