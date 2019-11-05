package id.co.kudoo_app_kotlin.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import id.co.kudoo_app_kotlin.model.TodoItem

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todos")
    fun loadAllTodos(): LiveData<List<TodoItem>> //wrap return type in livedata

    @Insert(onConflict = IGNORE)
    fun insertTodo(todo: TodoItem)

    @Delete
    fun deleteTodo(todo: TodoItem)
}