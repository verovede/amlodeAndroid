package com.example.amlode.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class DeaMarker(val id: Number, val type: String, val lat: Double, val long: Double, val date: Date) : Parcelable