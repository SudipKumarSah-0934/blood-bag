package com.blood_bag.model

import com.google.gson.annotations.SerializedName

data class DistrictListModel(
    @SerializedName("district_id"   ) var districtId   : Int?    = null,
    @SerializedName("district_name" ) var districtName : String? = null
)
