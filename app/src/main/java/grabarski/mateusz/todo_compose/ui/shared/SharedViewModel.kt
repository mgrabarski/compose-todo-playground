package grabarski.mateusz.todo_compose.ui.shared

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grabarski.mateusz.todo_compose.data.models.Priority
import grabarski.mateusz.todo_compose.data.models.ToDoTask
import grabarski.mateusz.todo_compose.data.repositories.DataStoreRepository
import grabarski.mateusz.todo_compose.data.repositories.ToDoRepository
import grabarski.mateusz.todo_compose.ui.navigation.Action
import grabarski.mateusz.todo_compose.ui.states.SearchAppBarState
import grabarski.mateusz.todo_compose.ui.states.SearchAppBarState.CLOSED
import grabarski.mateusz.todo_compose.ui.states.SearchAppBarState.TRIGGERED
import grabarski.mateusz.todo_compose.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedViewModel(
    private val toDoRepository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(CLOSED)
    val searchTextState: MutableState<String> = mutableStateOf("")

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(Idle)
    val allTask: StateFlow<RequestState<List<ToDoTask>>>
        get() = _allTasks

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>>
        get() = _allTasks

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        toDoRepository.getSortedByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        toDoRepository.getSortedByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?>
        get() = _selectedTask

    private val _sortState = MutableStateFlow<RequestState<Priority>>(Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun getAllTask() {
        _allTasks.value = Loading
        try {
            viewModelScope.launch {
                toDoRepository.getAllTasks.collect {
                    _allTasks.value = Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = Error(e)
        }
    }

    fun search(query: String) {
        _searchedTasks.value = Loading
        try {
            viewModelScope.launch {
                toDoRepository.search("%$query%").collect {
                    _searchedTasks.value = Success(it)
                }
            }
        } catch (e: Exception) {
            _searchedTasks.value = Error(e)
        }
        searchAppBarState.value = TRIGGERED
    }

    fun getTaskById(taskId: Int) {
        viewModelScope.launch {
            toDoRepository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?) {
        when {
            selectedTask != null -> {
                id.value = selectedTask.id
                title.value = selectedTask.title
                description.value = selectedTask.description
                priority.value = selectedTask.priority
            }
            else -> {
                id.value = 0
                title.value = ""
                description.value = ""
                priority.value = Priority.LOW
            }
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length < 20) {
            title.value = newTitle
        }
    }

    fun validateFiles(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    fun handleDatabaseAction(action: Action) {
        when (action) {
            Action.ADD, Action.UNDO -> addTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTasks()
            Action.NO_ACTION -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.addTask(
                ToDoTask(
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
            )
        }
        searchAppBarState.value = CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.updateTask(
                ToDoTask(
                    id = id.value,
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
            )
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteTask(
                ToDoTask(
                    id = id.value,
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
            )
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteAllTasks()
        }
    }

    fun persistSortingState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority)
        }
    }

    fun readSortState() {
        _sortState.value = Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = Error(e)
        }
    }
}
