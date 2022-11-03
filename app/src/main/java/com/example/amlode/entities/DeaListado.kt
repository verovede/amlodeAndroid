package com.example.amlode.entities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amlode.R


class DeaListado(direccion: String?, id: String?) {

    var direccion: String = ""
    var id: String = ""

    init {
        this.direccion = direccion!!
        this.id = id!!
    }
}