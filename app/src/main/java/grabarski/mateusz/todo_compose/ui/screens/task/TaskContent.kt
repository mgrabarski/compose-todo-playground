package grabarski.mateusz.todo_compose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import grabarski.mateusz.todo_compose.R
import grabarski.mateusz.todo_compose.data.models.Priority
import grabarski.mateusz.todo_compose.ui.components.PriorityDropDown
import grabarski.mateusz.todo_compose.ui.theme.LARGE_PADDING
import grabarski.mateusz.todo_compose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(text = stringResource(id = R.string.title))
            },
            singleLine = true
        )
        Spacer(
            modifier = Modifier
                .height(MEDIUM_PADDING)
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPriorityChange
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize(),
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(text = stringResource(id = R.string.description))
            },
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
private fun TaskContentPreview() {
    TaskContent(
        title = "title",
        onTitleChange = {},
        description = "description",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPriorityChange = {}
    )
}
