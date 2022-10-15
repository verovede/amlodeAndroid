package com.example.amlode

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    private var mapContext: Context = context
    private var view :View = LayoutInflater.from(mapContext).inflate(R.layout.custom_info_window, null)


    private fun rendowWindowText(marker: Marker, view: View){
        var title = marker.getTitle()
        var tvTitle = view.findViewById<TextView>(R.id.title_marker)

        if(!title.equals("")){
            tvTitle.setText(title)
        }

        var snippet = marker.getSnippet()
        var tvSnippets = view.findViewById<TextView>(R.id.text_marker)

        if(!snippet.equals("")){
            tvSnippets.setText(snippet)
        }
    }

    override fun getInfoContents(marker : Marker): View? {
        rendowWindowText(marker, view)
        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        rendowWindowText(marker, view)
        return view
    }
}