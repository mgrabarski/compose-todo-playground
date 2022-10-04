package grabarski.mateusz.todo_compose.data.repositories.impl

import grabarski.mateusz.todo_compose.data.database.dao.ToDoDao
import grabarski.mateusz.todo_compose.data.models.ToDoTask
import grabarski.mateusz.todo_compose.data.repositories.ToDoRepository
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {

    override val getAllTasks: Flow<List<ToDoTask>> = dao.getAllTasks()

    override val getSortedByLowPriority = dao.sortByLowPriority()

    override val getSortedByHighPriority = dao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> = dao.getById(taskId)

    override suspend fun addTask(toDoTask: ToDoTask) {
        dao.addTask(toDoTask)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        dao.updateTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        dao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }

    override fun search(query: String) = dao.searchDatabase(query)
}
