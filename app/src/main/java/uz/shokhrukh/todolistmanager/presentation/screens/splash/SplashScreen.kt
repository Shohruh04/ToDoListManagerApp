package uz.shokhrukh.todolistmanager.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getString
import kotlinx.coroutines.delay
import uz.shokhrukh.todolistmanager.R
import uz.shokhrukh.todolistmanager.presentation.theme.screenBackgroundColor
import java.io.File

@Composable
fun SplashScreen(
    onNavigateToMainScreen: () -> Unit,
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val cacheDir = context.getExternalFilesDir(null)
        val file = File(cacheDir, getString(context, R.string.app_name) + "images")
        if (!file.exists()) {
            file.mkdirs()
        }
        delay(2000)
        onNavigateToMainScreen()
    }
    Box(
        modifier = Modifier.fillMaxSize().background(screenBackgroundColor),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Todo List Manager",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}



@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen({})
}