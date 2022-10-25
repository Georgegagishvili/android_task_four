package com.example.taskfour
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(private val data: LinkedHashSet<String>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val deleteBtn: Button
        val preferences: SharedPreferences

        init {
            textView = view.findViewById(R.id.textView)
            deleteBtn = view.findViewById(R.id.deletebutton)
            preferences = view.context.getSharedPreferences("GENERAL", Context.MODE_PRIVATE)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.todo_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = data.elementAt(position)
        viewHolder.deleteBtn.setOnClickListener {
            data.remove(data.elementAt(position))
            notifyItemRemoved(position)
            println(data.toString())
            with(viewHolder.preferences.edit()) {
                putStringSet("TODOS", LinkedHashSet<String>(data))
                apply()
            }
        }
    }


    override fun getItemCount() = data.size
}