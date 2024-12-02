package pl.meksu.rentcar.presentation.main_activity.car_list.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Density

class CustomBottomRoundedShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - 60f)
            cubicTo(
                size.width * 0.95f, size.height,
                size.width * 0.05f, size.height,
                0f, size.height - 60f
            )
            close()
        }
        return Outline.Generic(path)
    }
}