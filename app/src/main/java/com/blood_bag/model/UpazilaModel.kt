package com.blood_bag.model

import com.google.gson.annotations.SerializedName

data class UpazilaModel(
    @SerializedName("status" ) var status : String?         = null,
    @SerializedName("data"   ) var data   : ArrayList<UpazilaListModel> = arrayListOf()
)
