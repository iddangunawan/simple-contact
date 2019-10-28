package com.mylektop.simplecontact.domain

import com.google.gson.annotations.SerializedName

/**
 * Created by MyLektop on 28/10/2019.
 */
class PutContactResponse : Response() {
    @SerializedName("data")
    var data: Contact? = null
}