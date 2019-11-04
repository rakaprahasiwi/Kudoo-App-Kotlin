package id.co.kudoo_app_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoItem(val title: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}