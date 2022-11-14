package com.example.md_assigment01_3009601

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // define recycler view
    private lateinit var mapRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize recyclerview variable
        mapRecyclerView = findViewById(R.id.mapRecyclerView)
        //Create layout Manager for list of User maps
        mapRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create adapter for displaying data from user
        mapRecyclerView.adapter = MapAdapter(this, emptyList())
    }
}