package grabarski.mateusz.todo_compose.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import grabarski.mateusz.todo_compose.data.database.dao.ToDoDao
import grabarski.mateusz.todo_compose.data.models.ToDoTask
import grabarski.mateusz.todo_compose.utils.Constants.DATABASE_NAME
import grabarski.mateusz.todo_compose.utils.Constants.DATABASE_VERSION

@Database(
    version = DATABASE_VERSION,
    exportSchema = true,
    entities = [ToDoTask::class]
)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun getToDoDao(): ToDoDao

    companion object {

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}
