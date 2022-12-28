package com.blood_bag.model

import com.google.gson.annotations.SerializedName

data class DivisionModel(
    @SerializedName("status" ) var status : String?         = null,
    @SerializedName("data"   ) var data   : ArrayList<DistrictListModel> = arrayListOf()
)
