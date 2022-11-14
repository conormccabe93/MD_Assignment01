package com.example.md_assigment01_3009601.models

// Data class for each Place a user selects from the google maps API
// Will take the a name and description along with the co-ordinates given by the Google Maps via its Long/Lat
data class Place(val title: String, val description: String, val latitude: Double, val longitude: Double)
