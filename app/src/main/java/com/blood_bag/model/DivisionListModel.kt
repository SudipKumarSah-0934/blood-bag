package com.blood_bag.model

import com.google.gson.annotations.SerializedName

data class DivisionListModel(
    @SerializedName("division_id"   ) var divisionId   : Int?    = null,
    @SerializedName("division_name" ) var divisionName : String? = null
)
