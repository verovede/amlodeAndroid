package com.example.amlode.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DeaListado(val direccion: String, val id: String) : Parcelable {

    val direccionItem: String = direccion
    val idItem: String =id

    init {

    }
}