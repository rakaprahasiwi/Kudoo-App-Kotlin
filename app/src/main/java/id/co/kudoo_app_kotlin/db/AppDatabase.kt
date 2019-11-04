package id.co.kudoo_app_kotlin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.co.kudoo_app_kotlin.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.newSingleThreadContext

val DB = newSingleThreadContext("DB") //coroutineContext for DB operations
val dbScope = CoroutineScope(DB)

@Database(entities = [TodoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(ctx: Context): AppDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(ctx, AppDatabase::class.java, "AppDatabase")
                    .build()
            }
            return INSTANCE!!
        }

        private fun prepopulateCallback(ctx: Context): Callback{
            return object : Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    populateWithSampleData(ctx)
                }
            }
        }

        private fun populateWithSampleData(ctx: Context) {
            dbScope.launch(DB) {
                with(getDatabase(ctx).todoItemDao()){
                    insertTodo(TodoItem("Create entitiy"))
                    insertTodo(TodoItem("Add a DAO for data access"))
                    insertTodo(TodoItem("inherit from RoomDatabase"))
                }
            }
        }
    }

    abstract fun todoItemDao(): TodoItemDao
}