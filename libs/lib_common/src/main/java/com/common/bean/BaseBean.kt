package com.common.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseBean:Parcelable {
    var isSelected = false
}