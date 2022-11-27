package com.example.md_assigment01_3009601

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.md_assigment01_3009601.databinding.ActivityCreateNewMapBinding
import com.example.md_assigment01_3009601.models.Place
import com.example.md_assigment01_3009601.models.UserCreatedMap
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.Snackbar

private const val TAG = "CreateNewMapActivity"

class CreateNewMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityCreateNewMapBinding
    private var userMarkers: MutableList<Marker> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNewMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set new map
        supportActionBar?.title = intent.getStringExtra(EXTRA_MAP_TITLE)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        // Only one snackbar can be used in a fragment
//        mapFragment.view?.let {
//            Snackbar.make(it,"Long Press to create a new marker", Snackbar.LENGTH_INDEFINITE)
//                .setAction("OK",{})
//                .setActionTextColor(ContextCompat.getColor(this,android.R.color.white))
//                .show()
//        }


        // Giving the user instructions on how to create/delete markers
        mapFragment.view?.let {
            Snackbar.make(it,"Long Press To create Marker! \nTap on Marker to delete!",Snackbar.LENGTH_INDEFINITE)
                .setAction("OK",{})
                .setActionTextColor(ContextCompat.getColor(this,android.R.color.white))
                .show()
        }

    }

    // Reference new menu file
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user_created_map,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Ensure item selected is the save item
        if (item.itemId == R.id.menuSave)  {
            Log.i(TAG,"click save >>")
            // Check if user has selected markers
            if (userMarkers.isEmpty()){
                Toast.makeText(this,"No markers selected", Toast.LENGTH_SHORT).show()
                return true
            }
            // Create new map
            val places = userMarkers.map { marker -> Place(marker.title,marker.snippet,marker.position.latitude,marker.position.longitude) }
            val userCreatedMap = UserCreatedMap(intent.getStringExtra(EXTRA_MAP_TITLE), places)
            val data = Intent()
            data.putExtra(EXTRA_USER_MAP,userCreatedMap)
            setResult(Activity.RESULT_OK,data)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set longclick listener to dialog popup
        mMap.setOnMapLongClickListener { LatLng ->

            // Show dialog option to confirm location marker
            showAlertDialog(LatLng)
        }

        // Delete function to remove marker by tapping on it
        mMap.setOnInfoWindowClickListener { markerToDelete ->
            Log.i(TAG,"onWindowClickListener -> delete marker")
            // remove from list of markers
            userMarkers.remove(markerToDelete)

            // remove marker from map
            markerToDelete.remove()
        }
    }

    private fun showAlertDialog(latLng: LatLng) {

        // Form input for user to create a title / description
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_marker,null)

        // Dialog popup to confirm if user wants selected location
        val dialogButton =
            AlertDialog.Builder(this)
                .setTitle("Create New Marker!")
                .setView(placeFormView)
                .setPositiveButton("Ok",null)
                .setNegativeButton("Cancel",null)
                .show()

        // If user selects yes, save location and add new maker
        dialogButton.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {

            // Get user input from dialog box
            val userMapName = placeFormView.findViewById<EditText>(R.id.userMapName).text.toString()
            val userDescription = placeFormView.findViewById<EditText>(R.id.userDescription).text.toString()

            // Check if both text blocks have been filled in
            if (userMapName.trim().isEmpty() || userDescription.trim().isEmpty()) {

                // prompt user to fill in both text blocks
                Toast.makeText( this,"Fill out Name / Description", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            } else {
                // Add new marker to the map
                val marker = mMap.addMarker(MarkerOptions().position(latLng).title(userMapName).snippet(userDescription))
                if (marker != null) {
                    userMarkers.add(marker)
                }
                // remove button when option is selected
                dialogButton.dismiss()
            }
        }
    }
}