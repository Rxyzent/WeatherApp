package com.rxyzent.weatherapp.core.model

import android.os.Parcel
import android.os.Parcelable

class CityData(var lat:Double,var lon:Double,var name:String?,var id:Int) {
    var isChecked =false
}