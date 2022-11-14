package com.example.md_assigment01_3009601

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.md_assigment01_3009601.models.Place
import com.example.md_assigment01_3009601.models.UserCreatedMap

class MainActivity : AppCompatActivity() {
    // define recycler view
    private lateinit var mapRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Call testData function for test
        val test1 = testData()

        // Initialize recyclerview variable
        mapRecyclerView = findViewById(R.id.mapRecyclerView)
        //Create layout Manager for list of User maps
        mapRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create adapter for displaying data from user
        mapRecyclerView.adapter = MapAdapter(this, test1)
    }

    private fun testData(): List<UserCreatedMap>{
        return listOf(
            UserCreatedMap("Test Map 1",
            listOf(
                Place("Site 1", "Site 1 Test description for random Co-crdinates", 1.212, -83.443)
            )
            )
        )
    }
}