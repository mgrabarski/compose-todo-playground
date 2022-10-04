package grabarski.mateusz.todo_compose.data.models

import androidx.compose.ui.graphics.Color
import grabarski.mateusz.todo_compose.ui.theme.HighPriorityColor
import grabarski.mateusz.todo_compose.ui.theme.LowPriorityColor
import grabarski.mateusz.todo_compose.ui.theme.MediumPriorityColor
import grabarski.mateusz.todo_compose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}
