package com.example.md_assigment01_3009601

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.md_assigment01_3009601.models.Place
import com.example.md_assigment01_3009601.models.UserCreatedMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

// GitHub REPO:  https://github.com/conormccabe93/MD_Assignment01.git

private const val TAG = "MainActivity"
const val EXTRA_USER_MAP = "EXTRA_USER_MAP"
const val EXTRA_MAP_TITLE = "EXTRA_MAP_TITLE"
private const val FILENAME = "UserCreatedMaps.data"
private const val REQUEST_CODE = 123

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var mapRecyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var userCreatedMaps: MutableList<UserCreatedMap>
    private lateinit var mapAdapter: MapAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Call testData function for test
        userCreatedMaps = testData().toMutableList()

        // Initialize recyclerview variable
        mapRecyclerView = findViewById(R.id.mapRecyclerView)
        floatingActionButton = findViewById(R.id.floatingActionButton)

        //Create layout Manager for list of User maps
        mapRecyclerView.layoutManager = LinearLayoutManager(this)

        // Create adapter for displaying data from user
        mapAdapter = MapAdapter(this, userCreatedMaps, object : MapAdapter.OnClickListener {
            // Log statement to show which map is selected
            override fun onItemClick(position: Int) {
                Log.i(TAG, "onItemClick $position")
                // Create a path to take user to new Activity ie. Map View of location
                val intent = Intent(this@MainActivity, DisplayUserMapActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, userCreatedMaps[position])
                startActivity(intent)
            }
        })
        mapRecyclerView.adapter = mapAdapter

        // Check if button click is registered
        floatingActionButton.setOnClickListener {
            showAlertDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Check if requestcode and resultcode match
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // return new map data
            val userCreatedMap = data?.getSerializableExtra(EXTRA_USER_MAP) as UserCreatedMap
            // add new map to list
            userCreatedMaps.add(userCreatedMap)
            // notify change in size
            mapAdapter.notifyItemInserted(userCreatedMaps.size - 1)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Returns file that methods read/write to
    private fun getDataFile(context: Context): File {
        Log.i(TAG,"Directory File > ${context.filesDir}")
        return File(context.filesDir, FILENAME)
    }

    // Create Sample data to test display
    private fun testData(): List<UserCreatedMap> {
        return listOf(
            UserCreatedMap(
                "Test Map 1 - Random",
                listOf(
                    Place(
                        "Site 1",
                        "Site 1 Test description for random Co-ordinates",
                        1.212,
                        -83.443
                    ),
                    Place(
                        "Site 2",
                        "Site 2 Test Description for Random co-ordinates",
                        1.999,
                        -82.111
                    )
                )
            ),
            UserCreatedMap(
                "Test Map 2 - Paris",
                listOf(
                    Place("Eiffel Tower", "The Eiffel Tower", 48.858, 2.294),
                    Place("The Louvre", "The Louvre Museum", 48.861, 2.332),
                    Place("Arch The Triumph", "The Arch The Triumph", 48.878, 2.296)
                )
            ),
            UserCreatedMap(
                "Test Map 3 - Dublin Ireland",
                listOf(
                    Place("Dublin Castle", "The Dublin Castle", 53.343, -6.267),
                    Place("Ha'penny Bridge", "The Ha'penny Bridge", 53.346, -6.263),
                    Place("O'Connell Bridge", " The O'Connell Bridge", 53.347, -6.259),
                    Place("Trinity College", "The Trinity College", 53.343, -6.254)
                )
            )
        )
    }

    private fun showAlertDialog() {
        // Form input for user to create a new map
        val mapFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)

        // Dialog popup to confirm if user wants selected location
        val dialogButton =
            AlertDialog.Builder(this)
                .setTitle("Create New Map!")
                .setView(mapFormView)
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show()

        // If user selects yes, save location and add new maker
        dialogButton.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            // Get user input from dialog box
            val newMapName = mapFormView.findViewById<EditText>(R.id.newMapTitle).text.toString()

            // Check if both text blocks have been filled in
            if (newMapName.trim().isEmpty()) {
                // prompt user to fill in both text blocks
                Toast.makeText(this, "Fill Out New Map Name!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val intent = Intent(this@MainActivity, CreateNewMapActivity::class.java)

            // Set new map name -> TEST NAME for now
            intent.putExtra(EXTRA_MAP_TITLE, newMapName)
            startActivityForResult(intent, REQUEST_CODE)
                // remove button when option is selected
                dialogButton.dismiss()
            }
        }
    }
