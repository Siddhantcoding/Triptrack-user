import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.trip_track_project.R
import kotlinx.coroutines.delay

@Composable
fun WelcomeSplashScreen(onSplashEnd: () -> Unit) {
    val alpha = remember { Animatable(0f) }

    // Start increasing the alpha value as soon as this composable gets loaded
    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000)
        )
        delay(3000) // Keep the splash screen for 3 seconds
        onSplashEnd()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface),
            painter = painterResource(id = R.drawable.triptrack2), // Replace with your image resource
            contentDescription = "Splash Image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeSplashScreenPreview() {
    WelcomeSplashScreen {

    }
}