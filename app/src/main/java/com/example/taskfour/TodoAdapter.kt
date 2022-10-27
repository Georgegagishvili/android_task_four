package com.example.taskfour

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class TodoAdapter(private val data: LinkedHashSet<TodoItem>) :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val deleteBtn: Button
        val preferences: SharedPreferences

        init {
            titleText = view.findViewById(R.id.textView)
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
        viewHolder.titleText.text = data.elementAt(position).title
        viewHolder.deleteBtn.setOnClickListener {
            delete(viewHolder.preferences, position)
        }
        viewHolder.itemView.setOnClickListener {
            onTodoClick(viewHolder, data.elementAt(position))
        }
    }

    private fun delete(preferences: SharedPreferences, position: Int) {
        data.remove(data.elementAt(position))
        notifyItemRemoved(position)
        println(data.toString())
        with(preferences.edit()) {
            putStringSet("TODOS", LinkedHashSet<String>(data.map { Gson().toJson(it) }))
            apply()
        }
    }

    private fun onTodoClick(view: ViewHolder, toDo: TodoItem) {
        val intent = Intent(view.itemView.context, TodoActivity::class.java);
        intent.putExtra("todo", Gson().toJson(toDo))
        view.itemView.context.startActivity(intent);
    }


    override fun getItemCount() = data.size
}