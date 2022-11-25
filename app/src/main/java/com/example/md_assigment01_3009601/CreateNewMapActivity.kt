package com.example.md_assigment01_3009601

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        // Log statement to show longclick
        mMap.setOnMapLongClickListener { LatLng ->
            
            // Log.i(TAG,"setOnMapLongClickListener")

            // Show dialog option to confirm location marker
            showAlertDialog(LatLng)

        }
    }


    private fun showAlertDialog(latLng: LatLng) {

        // Dialog popup to confirm if user wants selected location
        val dialogButton =
            AlertDialog.Builder(this)
            .setTitle("CREATE NEW MARKER")
            .setMessage("TEST MESSAGE")
            .setPositiveButton("OK",null)
            .setNegativeButton("CANCEL",null)
            .show()

        // If user selects yes, save location and add new maker
        dialogButton.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title("TEST MARKER LONGCLICK").snippet("TEST SNIPPET"))
            if (marker != null) {
                userMarkers.add(marker)
            }
            // remove button when option is selected
            dialogButton.dismiss()
        }


    }
}