package id.co.kudoo_app_kotlin.view.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.kudoo_app_kotlin.R
import id.co.kudoo_app_kotlin.db.dbScope
import id.co.kudoo_app_kotlin.model.TodoItem
import id.co.kudoo_app_kotlin.view.common.getViewModel
import id.co.kudoo_app_kotlin.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.coroutines.launch

class AddTodoActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        viewModel = getViewModel(TodoViewModel::class)
        setUpListener()
    }

    private fun setUpListener() {
        btnAddTodo.setOnClickListener {
            val newTodo = etNewTodo.text.toString()
            dbScope.launch {
                viewModel.add(TodoItem(newTodo))
            }
            finish()
        }
    }
}
