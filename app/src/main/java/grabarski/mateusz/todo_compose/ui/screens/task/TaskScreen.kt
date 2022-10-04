package grabarski.mateusz.todo_compose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import grabarski.mateusz.todo_compose.data.models.Priority
import grabarski.mateusz.todo_compose.data.models.ToDoTask
import grabarski.mateusz.todo_compose.ui.navigation.Action
import grabarski.mateusz.todo_compose.ui.shared.SharedViewModel

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen ={ action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFiles()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                }
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.description.value = it
                },
                priority = priority,
                onPriorityChange = {
                    sharedViewModel.priority.value = it
                }
            )
        }
    )
}

private fun displayToast(context: Context) {
    Toast.makeText(context, "Fields empty.", Toast.LENGTH_LONG).show()
}
