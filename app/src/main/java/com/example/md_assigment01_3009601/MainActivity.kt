package com.example.md_assigment01_3009601

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.md_assigment01_3009601.models.Place
import com.example.md_assigment01_3009601.models.UserCreatedMap

// GitHub REPO:  https://github.com/conormccabe93/MD_Assignment01.git

private const val TAG = "MainActivity"

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
        mapRecyclerView.adapter = MapAdapter(this, test1, object: MapAdapter.OnClickListener{
            // Log statement to show which map is selected
            override fun onItemClick(position: Int) {
                Log.i(TAG,"onItemClick $position")
            }

        })
    }

    // Create Sample data to test display
    private fun testData(): List<UserCreatedMap>{
        return listOf(
            UserCreatedMap("Test Map 1 - Random",
                listOf(
                    Place("Site 1", "Site 1 Test description for random Co-ordinates", 1.212, -83.443),
                    Place("Site 2", "Site 2 Test Description for Random co-ordinates",1.999,-82.111 )
                    )
                ),
            UserCreatedMap("Test Map 2 - Paris",
                listOf(
                    Place("Eiffel Tower", "The Eiffel Tower", 48.858,2.294),
                    Place("The Louvre", "The Louvre Museum",48.861, 2.332 ),
                    Place("Arch The Triumph", "The Arch The Triumph", 48.878, 2.296)
                    )
                ),
            UserCreatedMap("Test Map 3 - Dublin Ireland",
                listOf(
                    Place("Dublin Castle", "The Dublin Castle",53.343, -6.267),
                    Place("Ha'penny Bridge", "The Ha'penny Birdge",53.346, -6.263),
                    Place("O'Connell Bridge", " The O'Connell Brdige",53.347, -6.259),
                    Place("Trinity College", "The Trinity College", 53.343, -6.254)
                    )
                )
        )
    }
}