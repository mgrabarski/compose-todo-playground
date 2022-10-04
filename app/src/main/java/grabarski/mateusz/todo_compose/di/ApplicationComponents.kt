package grabarski.mateusz.todo_compose.di

import grabarski.mateusz.todo_compose.data.database.TodoDatabase
import grabarski.mateusz.todo_compose.data.repositories.DataStoreRepository
import grabarski.mateusz.todo_compose.data.repositories.ToDoRepository
import grabarski.mateusz.todo_compose.data.repositories.impl.ToDoRepositoryImpl
import grabarski.mateusz.todo_compose.ui.shared.SharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATABASE = "DATABASE"

val appModule = module {
    single(named(DATABASE)) { TodoDatabase.buildDatabase(androidContext()) }
    factory { (get(named(DATABASE)) as TodoDatabase).getToDoDao() }

    factory<ToDoRepository> { ToDoRepositoryImpl(get()) }

    viewModel { SharedViewModel(get(), get()) }

    factory { DataStoreRepository(androidContext()) }
}
