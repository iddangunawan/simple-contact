package com.mylektop.simplecontact.domain

import com.google.gson.annotations.SerializedName

/**
 * Created by MyLektop on 25/10/2019.
 */
class GetContactResponse : Response() {
    @SerializedName("data")
    var data: List<Contact>? = null
}