package grabarski.mateusz.todo_compose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import grabarski.mateusz.todo_compose.utils.Constants.DATABASE_TABLE_TODO

@Entity(tableName = DATABASE_TABLE_TODO)
data class ToDoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority
)
