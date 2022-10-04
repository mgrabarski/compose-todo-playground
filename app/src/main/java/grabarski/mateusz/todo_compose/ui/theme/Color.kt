package grabarski.mateusz.todo_compose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGrey = Color(0xFFCFCFCF)
val MediumGrey = Color(0xFF707070)
val DarkGrey = Color(0xFF282728)

val LowPriorityColor = Color.Green
val MediumPriorityColor = Color.Yellow
val HighPriorityColor = Color.Red
val NonePriorityColor = MediumGrey

val Colors.topBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else LightGrey

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGrey

val Colors.taskItemTextColor: Color
    @Composable
    get() = if (isLight) DarkGrey else LightGrey

val Colors.splashScreenBackground: Color
    @Composable
    get() = if (isLight) Purple700 else DarkGrey
