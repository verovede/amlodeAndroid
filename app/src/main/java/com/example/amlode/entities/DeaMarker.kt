package com.example.amlode.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DeaMarker(val id: String, val lat: Double, val long: Double, val active: Boolean, val date: String, val address: String) : Parcelable