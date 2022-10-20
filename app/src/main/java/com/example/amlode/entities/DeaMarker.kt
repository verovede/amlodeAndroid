package com.example.amlode.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DeaMarker(val id: Number, val lat: Double, val long: Double, val owner: String, val info: String, val device: String) : Parcelable