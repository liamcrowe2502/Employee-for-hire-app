package org.wit.placemark.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeModel(var id: Long = 0,
                         var name: String = "",
                         var bio: String = "",
                         var email: String = "",
                         var phone: Long = 0,
                         var work: Boolean = false,
                         var image: Uri = Uri.EMPTY,
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable