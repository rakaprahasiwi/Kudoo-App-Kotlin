package id.co.kudoo_app_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.kudoo_app_kotlin.db.dbScope
import id.co.kudoo_app_kotlin.model.TodoItem
import id.co.kudoo_app_kotlin.view.add.AddTodoActivity
import id.co.kudoo_app_kotlin.view.common.getViewModel
import id.co.kudoo_app_kotlin.view.main.RecyclerListAdapter
import id.co.kudoo_app_kotlin.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var viewModel: TodoViewModel  // Now references view model, not DB

    val uiScope = CoroutineScope(coroutineContext + SupervisorJob())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = getViewModel(TodoViewModel::class)  // getViewModel is impl. next
        setUpRecyclerView()
        setUpFloatingActionButton()
    }

    private fun setUpFloatingActionButton() {
        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpRecyclerView() {
        with(recyclerViewTodos) {
            adapter = RecyclerListAdapter(mutableListOf(), onRecylerItemClick())
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            )
        }

        uiScope.launch {
            val todosLiveData = viewModel.getTodos()
            todosLiveData.observe(this@MainActivity, Observer { todos ->
                todos?.let {
                    val adapter = (recyclerViewTodos.adapter as RecyclerListAdapter)
                    adapter.setItems(it)
                }
            })
        }
    }

    private fun onRecylerItemClick(): (TodoItem) -> Unit = { todo ->
        dbScope.launch { viewModel.delete(todo) }
    }
}
