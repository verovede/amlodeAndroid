package com.example.amlode.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import com.example.amlode.adapters.DeaListAdapter
import com.example.amlode.api.APIService
import com.example.amlode.data.DeaResponse
import com.example.amlode.data.UserResponse
import com.example.amlode.entities.DeaListado
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // recibe por args el mail del usuario
        val idUser = DeaListFragmentArgs.fromBundle(requireArguments()).mailUser
        Snackbar.make(v,idUser,Snackbar.LENGTH_SHORT).show()

        // busco los deas por el mail del usuario
        val apiUser = APIService.createUserAPI()
        apiUser.getUser("v2/entities/${prefs.getEmail()}?type=user")
            ?.enqueue(object : Callback<UserResponse?> {
                override fun onResponse(call: Call<UserResponse?>, user: Response<UserResponse?>) {
                    val user: UserResponse? = (user.body())!!
                    if (user != null) {
                        val deasInUser= user.deas.value
                        for(dea in deasInUser){
                            buscarDeaPorId(dea)

                        }
                    }
                }
                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Get")
                }
            })


       /* for (i in 1..10) {

            deas.add(DeaListado("hola", "hola"  ))
        }*/


        recDeas.setHasFixedSize(true)

        linearlayourManager = LinearLayoutManager(context)

        recDeas.layoutManager = linearlayourManager

        recDeas.adapter = DeaListAdapter(deas){ x -> onItemClick(x)
        }
    }

    fun onItemClick( position : Int) : Boolean{
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }

    private fun buscarDeaPorId(id:String) {
        val apiDea = APIService.createDeaAPI()
        apiDea.getDea("v2/entities/${id}?type=dea")
            ?.enqueue(object : Callback<DeaResponse?> {
                override fun onResponse(call: Call<DeaResponse?>, user: Response<DeaResponse?>) {
                    val dea: DeaResponse? = (user.body())!!
                    if (dea != null) {
                        deas.add(DeaListado( " ${dea.address}", "${dea.id}"))
                        deas.add(DeaListado( " caca", "caca2"))
                        Log.d("Deas ver","${deas}")

                    }
                }

                override fun onFailure(call: Call<DeaResponse?>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Get")
                }
            })

    }

}