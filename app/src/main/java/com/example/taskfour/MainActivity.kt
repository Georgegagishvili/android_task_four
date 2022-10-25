package com.example.taskfour

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var data: LinkedHashSet<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TodoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        data = LinkedHashSet()
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPref = getPreferences(MODE_PRIVATE)
        val savedTodos = sharedPref.getStringSet("TODOS", LinkedHashSet<String>())
        if (savedTodos != null) {
            data = LinkedHashSet(savedTodos)
        }

        adapter = TodoAdapter(data)
        recyclerView.adapter = adapter
    }

    fun onButtonClick(view: View) {
        if (view is Button) {
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            data.add(editText.text.toString())
            with(sharedPref.edit()) {
                putStringSet("TODOS", LinkedHashSet<String>(data))
                commit()
            }
            recyclerView.adapter = adapter
        }
    }
}