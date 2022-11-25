package com.example.md_assigment01_3009601

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.md_assigment01_3009601.databinding.ActivityCreateNewMapBinding
import com.google.android.gms.maps.model.Marker

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