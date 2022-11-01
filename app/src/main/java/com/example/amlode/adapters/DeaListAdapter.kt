package com.example.amlode.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amlode.R
import com.example.amlode.entities.DeaListado
import com.example.amlode.holders.DeaHolder

class DeaListAdapter(
    var deasList: MutableList<DeaListado>,
    val onItemClick: (Int) -> Boolean
): RecyclerView.Adapter<DeaHolder>(){

    override fun getItemCount(): Int{
        return deasList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dea_listado,parent,false)
        return (DeaHolder(view))
    }

    override fun onBindViewHolder(holder: DeaHolder, position: Int) {
        holder.setAdress(deasList[position].direccion)
        holder.setId(deasList[position].id)
        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

    fun setData(newData: ArrayList<DeaListado>){
        this.deasList = newData
        this.notifyDataSetChanged()
    }
}

