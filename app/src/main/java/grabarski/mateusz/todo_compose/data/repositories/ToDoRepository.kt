package grabarski.mateusz.todo_compose.data.repositories

import grabarski.mateusz.todo_compose.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    val getAllTasks: Flow<List<ToDoTask>>
    val getSortedByLowPriority: Flow<List<ToDoTask>>
    val getSortedByHighPriority: Flow<List<ToDoTask>>
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>
    suspend fun addTask(toDoTask: ToDoTask)
    suspend fun updateTask(toDoTask: ToDoTask)
    suspend fun deleteTask(toDoTask: ToDoTask)
    suspend fun deleteAllTasks()
    fun search(query: String): Flow<List<ToDoTask>>
}
