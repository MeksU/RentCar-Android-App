package pl.meksu.rentcar.presentation.logged_user_activity.home_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import coil.compose.rememberAsyncImagePainter
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.presentation.ui.theme.MainBlue

@Composable
fun OfferItem(
    offer: Offer,
    onOfferClick: (Int) -> Unit
) {
    val imageUrl = Constants.IMAGE_URL + offer.car.image

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp)
            .clickable {
                onOfferClick(offer.id)
            }
            .shadow(3.dp, shape = RoundedCornerShape(16.dp))
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .height(140.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${offer.car.brand} ${offer.car.model}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "skrzynia ${offer.car.transmission}",
                            fontWeight = FontWeight.Bold,
                            color = Color.LightGray,
                            fontSize = 10.sp
                        )
                    }
                    Column(
                        Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Row(
                                modifier = Modifier
                                    .shadow(3.dp, shape = RoundedCornerShape(8.dp))
                                    .background(color = MainBlue, shape = RoundedCornerShape(8.dp))
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .wrapContentSize()
                            ) {
                                Text(
                                    text = "${offer.price.fastRoundToInt()}zł",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Text(
                                    text = "/dzień",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                            if (offer.promotion > 0.0) {
                                Text(
                                    modifier = Modifier.padding(8.dp),
                                    text = "${offer.promotion.fastRoundToInt()}zł",
                                    color = Color.Red,
                                    fontSize = 12.sp,
                                    style = TextStyle(
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Car Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            if (offer.promotion > 0.0) {
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = "PROMOCJA!",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}