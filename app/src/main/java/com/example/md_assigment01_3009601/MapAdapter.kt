package com.example.md_assigment01_3009601

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.md_assigment01_3009601.models.UserCreatedMap
import com.google.android.material.tabs.TabLayout.TabGravity

private const val TAG = "MapAdapter"

// Intialize class with variables to take a list of user created maps
class MapAdapter(val context: Context, val userCreatedMaps: List<UserCreatedMap>, val onClickListener: OnClickListener ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create new view using Inflater with a default list
        val viewpoint = LayoutInflater.from(context).inflate(R.layout.item_map,parent,false)
        return ViewHolder(viewpoint)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Create variable for each map data and set it to a specific position in the view
        val userMap = userCreatedMaps[position]
        // Create clickListener to notify when user taps on a Map
        holder.itemView.setOnClickListener{
            // Using Log to show item
            Log.i(TAG,"User selected  > $position")
            onClickListener.onItemClick(position)
        }
        val textViewTitle = holder.itemView.findViewById<TextView>(R.id.map_View)
        textViewTitle.text = userMap.title
    }

    // Returns the amount of user maps the user has created
    override fun getItemCount() = userCreatedMaps.size
}