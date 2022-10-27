package com.example.taskfour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson

class TodoActivity : AppCompatActivity() {
    private lateinit var titleText : TextView
    private lateinit var descText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
        init()
        setData()
    }

    private fun init(){
        titleText = findViewById(R.id.todoTitle)
        descText = findViewById(R.id.todoDesc)
    }

    private fun setData(){
        val todoItem = Gson().fromJson(intent.getStringExtra("todo"),TodoItem::class.java)
        titleText.text = todoItem.title
        descText.text = todoItem.description
    }
}