package com.mylektop.simplecontact.domain

import com.google.gson.annotations.SerializedName

/**
 * Created by MyLektop on 27/10/2019.
 */
open class Response {
    @SerializedName("message")
    var message: String = ""
}