package uz.shokhrukh.todolistmanager.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.shokhrukh.todolistmanager.presentation.theme.DisabledButtonColor
import uz.shokhrukh.todolistmanager.presentation.theme.MainColor

@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    containerColor: Color = MainColor,
    textSize: TextUnit = 14.sp,
    enabled: Boolean = false,
    text: String = "Create",
    onClick: () -> Unit = {}
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = DisabledButtonColor
        )
    ) {
        Text(
            text = text,
            fontSize = textSize,
            color = if (!enabled) Color.Gray else Color.White
        )
    }
}

@Preview
@Composable
private fun ButtonViewPreview() {
    ButtonView(
        modifier = Modifier.fillMaxWidth()
    )
}