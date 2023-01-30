package com.jhonjson.common

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class RouteData : Parcelable {
    companion object {
        const val ROUTE_DATA = "ROUTE_DATA"
    }
}