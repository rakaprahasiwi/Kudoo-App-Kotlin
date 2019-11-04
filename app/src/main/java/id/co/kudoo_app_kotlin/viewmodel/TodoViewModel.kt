package id.co.kudoo_app_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import id.co.kudoo_app_kotlin.db.AppDatabase
import id.co.kudoo_app_kotlin.db.DB
import id.co.kudoo_app_kotlin.db.dbScope
import id.co.kudoo_app_kotlin.model.TodoItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(app: Application) : AndroidViewModel(app) {
    private val dao by lazy { AppDatabase.getDatabase(getApplication()).todoItemDao() }

    suspend fun getTodos(): MutableList<TodoItem> = withContext(DB) {
        dao.loadAllTodos().toMutableList()
    }

    fun add(todo: TodoItem) = dbScope.launch { dao.insertTodo(todo) }

    fun delete(todo: TodoItem) = dbScope.launch { dao.deleteTodo(todo) }
}