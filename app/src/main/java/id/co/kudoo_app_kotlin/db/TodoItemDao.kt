package id.co.kudoo_app_kotlin.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import id.co.kudoo_app_kotlin.model.TodoItem

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todos")
    fun loadAllTodos(): List<TodoItem>

    @Insert(onConflict = IGNORE)
    fun insertTodo(todo: TodoItem)

    @Delete
    fun deleteTodo(todo: TodoItem)
}