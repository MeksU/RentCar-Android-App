package pl.meksu.rentcar.presentation.main_activity.car_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.domain.model.Car

@Composable
fun CarListItem(car: Car) {
    Column(
        modifier = Modifier.fillMaxWidth().background(color = Color.White)
    ) {
        val imageUrl = Constants.IMAGE_URL + car.image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Car Image",
                    Modifier.fillMaxWidth().height(260.dp),
                    contentScale = ContentScale.Fit
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = car.brand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = car.model,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF009aeb),
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}