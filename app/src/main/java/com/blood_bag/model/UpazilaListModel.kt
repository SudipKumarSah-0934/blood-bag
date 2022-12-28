package com.blood_bag.model

import com.google.gson.annotations.SerializedName

data class UpazilaListModel(
    @SerializedName("upazila_id"   ) var upazilaId   : Int?    = null,
    @SerializedName("upazila_name" ) var upazilaName : String? = null
)
