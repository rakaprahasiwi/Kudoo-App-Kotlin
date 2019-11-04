package id.co.kudoo_app_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.co.kudoo_app_kotlin.main.RecyclerListAdapter
import id.co.kudoo_app_kotlin.model.TodoItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpRecyclerView()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setUpRecyclerView() = with(recyclerViewTodos){
        adapter = RecyclerListAdapter(sampleData())
        layoutManager = LinearLayoutManager(this@MainActivity)
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    }

    private fun sampleData() = mutableListOf(
        TodoItem("Implement RecyclerView"),
        TodoItem("Store to-dos in database"),
        TodoItem("Detele to-dos on click")
    )

}
