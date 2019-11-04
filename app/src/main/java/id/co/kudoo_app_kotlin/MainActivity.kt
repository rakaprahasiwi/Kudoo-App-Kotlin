package id.co.kudoo_app_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.co.kudoo_app_kotlin.db.dbScope
import id.co.kudoo_app_kotlin.model.TodoItem
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

        dbScope.launch {
            viewModel.add(TodoItem("Won't show up automatically yet"))
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setUpRecyclerView() = with(recyclerViewTodos) {
        uiScope.launch {
            adapter = RecyclerListAdapter(viewModel.getTodos())
        }
        layoutManager = LinearLayoutManager(this@MainActivity)
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    }
}
