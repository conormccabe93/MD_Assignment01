package com.example.md_assigment01_3009601.models

import java.io.Serializable

// Data class for each new map a User creates
// Includes Name of map and each place they choose
data class UserCreatedMap(val title: String, val places: List<Place>) : Serializable