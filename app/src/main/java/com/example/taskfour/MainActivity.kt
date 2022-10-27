package com.example.taskfour

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    private lateinit var titleText: EditText
    private lateinit var descText: EditText
    private lateinit var data: LinkedHashSet<TodoItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        data = LinkedHashSet()
        titleText = findViewById(R.id.titleEditText)
        descText = findViewById(R.id.descEditText)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        sharedPref = getSharedPreferences("GENERAL", Context.MODE_PRIVATE)

        val savedTodos = sharedPref.getStringSet("TODOS", LinkedHashSet<String>())
        if (savedTodos != null) {
            data = LinkedHashSet(savedTodos.map {
                Gson().fromJson(it, TodoItem::class.java)
            })
        }

        adapter = TodoAdapter(data)
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onButtonClick(view: View) {
        val gson = Gson()
        if (view is Button) {
            data.add(
                TodoItem(
                    titleText.text.toString(),
                    descText.text.toString()
                )
            )
            with(sharedPref.edit()) {
                putStringSet("TODOS", LinkedHashSet<String>(data.map {
                    gson.toJson(it)
                }))
                commit()
            }

            adapter.notifyDataSetChanged()
        }
    }
}