package com.punokawan.eazypark.dummydata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryData (
    var location:String,
    var timeIn:String,
    var timeOut:String,
    var date:String,
    var parking_time:String
): Parcelable