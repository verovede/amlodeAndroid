package com.example.amlode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amlode.R
import com.example.amlode.adapters.DeaListAdapter
import com.example.amlode.entities.DeaListado
import com.google.android.material.snackbar.Snackbar

class  DeaListFragment : Fragment() {

    lateinit var v: View
    lateinit var recDeas: RecyclerView
    var deas: MutableList<DeaListado> = ArrayList<DeaListado>()

    private lateinit var linearlayourManager: LinearLayoutManager
    private lateinit var deaListAdapter: DeaListAdapter

    companion object {
        fun newInstance() = DeaListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dea_list, container, false)
        recDeas = v.findViewById(R.id.rec_dea)
        return v
    }

    override fun onStart(){
        super.onStart()

        //for(i in 1≤..≤10 ){
        //}

        //Hardcodeado para testeo
        deas.add(DeaListado( "Manzanares 3271", 1))
        deas.add(DeaListado( "Calle falsa 123", 2))
        deas.add(DeaListado( "Maipu 1855", 3))
        deas.add(DeaListado( "Sarlanga 32C", 4))

        recDeas.setHasFixedSize(true)
        linearlayourManager = LinearLayoutManager(context)
        recDeas.layoutManager = linearlayourManager
        deaListAdapter = DeaListAdapter(deas){ x ->
            onItemClick(x)
        }
    }

    fun onItemClick( position : Int) : Boolean{
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }
}