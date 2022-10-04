package grabarski.mateusz.todo_compose

import android.app.Application
import grabarski.mateusz.todo_compose.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToDoApplication)
            modules(appModule)
        }
    }
}
