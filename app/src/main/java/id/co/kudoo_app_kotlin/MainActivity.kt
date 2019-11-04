package id.co.kudoo_app_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.co.kudoo_app_kotlin.db.AppDatabase
import id.co.kudoo_app_kotlin.db.DB
import id.co.kudoo_app_kotlin.main.RecyclerListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var db: AppDatabase //store an AppDatabase object
    val uiScope = CoroutineScope(coroutineContext + SupervisorJob())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        db = AppDatabase.getDatabase(applicationContext)
        setUpRecyclerView() //sets up rc after db reference initialized

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setUpRecyclerView() = with(recyclerViewTodos){
        uiScope.launch {
            val todos = sampleData().toMutableList()
            adapter = RecyclerListAdapter(todos)
        }
        layoutManager = LinearLayoutManager(this@MainActivity)
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    }

    private suspend fun sampleData() = withContext(DB){ db.todoItemDao().loadAllTodos()} //use db context

}
