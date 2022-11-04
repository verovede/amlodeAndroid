package com.example.amlode.holders

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.amlode.R

class DeaHolder(v: View) : RecyclerView.ViewHolder(v){

    private var view: View

    init{
        this.view = v
    }

    fun setAdress(address: String){
        val txt: TextView = view.findViewById(R.id.item_dea_adresss)
        txt.text = "Dirección:$address"
    }

    fun setId(id: String){
        val txt: TextView = view.findViewById(R.id.item_dea_id)
        txt.text = "ID: $id"
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_package_item)
    }
}