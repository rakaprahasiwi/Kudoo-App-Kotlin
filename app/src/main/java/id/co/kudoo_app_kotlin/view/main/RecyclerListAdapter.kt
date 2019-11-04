package id.co.kudoo_app_kotlin.view.main

import androidx.recyclerview.widget.RecyclerView
import android.view.*
import id.co.kudoo_app_kotlin.R
import id.co.kudoo_app_kotlin.model.TodoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.todo_item.*

class RecyclerListAdapter(
    private val items: MutableList<TodoItem>
) : RecyclerView.Adapter<RecyclerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(todoItem: TodoItem) {
            tvTodoTitle.text = todoItem.title
            cbTodoDone.isChecked = false  // To-do items are always 'not done' (or deleted)
        }
    }
}