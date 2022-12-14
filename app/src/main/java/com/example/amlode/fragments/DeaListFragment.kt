package com.example.amlode.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amlode.R
import com.example.amlode.adapters.DeaListAdapter
import com.example.amlode.entities.DeaListado

class DeaListFragment : Fragment() {

    lateinit var v: View
    lateinit var recDeas: RecyclerView
    private lateinit var linearlayourManager: LinearLayoutManager
    private lateinit var deaListAdapter: DeaListAdapter
    private var deas: ArrayList<DeaListado> = ArrayList()

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

    override fun onStart() {
        super.onStart()
        // recibe por args el mail del usuario
        val deaArgument = DeaListFragmentArgs.fromBundle(requireArguments()).deaList

        if (!deaArgument.isEmpty()) {
            for (dea in deaArgument) {
                if (!verificateDea(dea.id)) {
                deas.add(dea)}
            }
        } else {
            Toast.makeText(context, "No tenés ningún DEA registrado!", Toast.LENGTH_LONG)
                .show()
        }

        Log.d("DEAS ", "${deas}")
        recDeas.setHasFixedSize(true)
        linearlayourManager = LinearLayoutManager(context)
        recDeas.layoutManager = linearlayourManager
        recDeas.adapter = DeaListAdapter(deas) { x -> onItemClick(x) }
    }

    fun onItemClick(position: Int): Boolean {
        //Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun verificateDea(idDea: String): Boolean {
        for (dea in deas) {
            if (idDea == dea.id) {
                return true
            }
        }
        return false
    }

}
