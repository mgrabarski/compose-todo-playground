package grabarski.mateusz.todo_compose.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import grabarski.mateusz.todo_compose.ui.theme.TodocomposeTheme
import grabarski.mateusz.todo_compose.ui.theme.splashScreenBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetState by animateDpAsState(
        targetValue = if (startAnimation) {
            0.dp
        } else {
            100.dp
        },
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    val alphaState by animateFloatAsState(
        targetValue = if (startAnimation) {
            1f
        } else {
            0f
        },
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3_000)
        navigateToListScreen()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.splashScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(size = 100.dp)
                .offset(y = offsetState)
                .alpha(alpha = alphaState),
            imageVector = Icons.Filled.Check,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(navigateToListScreen = {})
}


@Preview
@Composable
private fun SplashScreenDarkPreview() {
    TodocomposeTheme(darkTheme = true) {
        SplashScreen(navigateToListScreen = {})
    }
}
