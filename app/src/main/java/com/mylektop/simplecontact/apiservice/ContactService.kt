package com.mylektop.simplecontact.apiservice

import com.mylektop.simplecontact.domain.GetContactResponse
import com.mylektop.simplecontact.domain.PostContactBody
import com.mylektop.simplecontact.domain.PutContactResponse
import com.mylektop.simplecontact.domain.Response
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Path

/**
 * Created by MyLektop on 25/10/2019.
 */
interface ContactService {
    interface GET {
        @retrofit2.http.GET("contact")
        fun getContact(): Observable<GetContactResponse>
    }

    interface POST {
        @retrofit2.http.POST("contact")
        fun postContact(@Body postContactBody: PostContactBody): Observable<Response>
    }

    interface DELETE {
        @retrofit2.http.DELETE("contact/{id}")
        fun deleteContact(@Path("id") id: String): Observable<Response>
    }

    interface PUT {
        @retrofit2.http.PUT("contact/{id}")
        fun putContact(@Path("id") id: String, @Body postContactBody: PostContactBody): Observable<PutContactResponse>
    }
}