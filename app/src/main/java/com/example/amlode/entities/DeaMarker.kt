package com.example.amlode.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DeaMarker(val id: String, val latitude: Double, val longitude: Double, val active: Boolean, val datestamp: String, val address: String) : Parcelable