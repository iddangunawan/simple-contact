package com.mylektop.simplecontact.domain

import com.google.gson.annotations.SerializedName

/**
 * Created by MyLektop on 28/10/2019.
 */
class PostContactBody {
    @SerializedName("firstName")
    var firstName: String = ""
    @SerializedName("lastName")
    var lastName: String = ""
    @SerializedName("age")
    var age: Int = 0
    @SerializedName("photo")
    var photo: String = ""
}