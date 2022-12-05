package com.example.md_assigment01_3009601

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.md_assigment01_3009601.databinding.ActivityDisplayUserMapBinding
import com.example.md_assigment01_3009601.models.UserCreatedMap
import com.google.android.gms.maps.model.LatLngBounds

private const val TAG = "DisplayUserMapActivity"

@Suppress("DEPRECATION")
class DisplayUserMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDisplayUserMapBinding
    private lateinit var userCreatedMap: UserCreatedMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayUserMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get map from intent
        userCreatedMap = intent.getSerializableExtra(EXTRA_USER_MAP) as UserCreatedMap

        // Update title of screen to show title of selected map
        supportActionBar?.title = userCreatedMap.title

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set display of maps to focus on area where the locations are
        val boundsBuilder = LatLngBounds.Builder()

        // Create location markers for each spot on individual maps
        for (place in userCreatedMap.places){
            val latLng = LatLng(place.latitude,place.longitude)
            boundsBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(place.title).snippet(place.description))
        }
        // Set camera move to centre focus on user locations
        // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),500,500,0))

        // Animation of camera to zoom in on markers
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),500,500,0))
    }
}