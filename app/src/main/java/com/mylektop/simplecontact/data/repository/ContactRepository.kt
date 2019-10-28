package com.mylektop.simplecontact.data.repository

import com.mylektop.simplecontact.apiservice.ContactService
import com.mylektop.simplecontact.domain.GetContactResponse
import com.mylektop.simplecontact.domain.PostContactBody
import com.mylektop.simplecontact.domain.PutContactResponse
import com.mylektop.simplecontact.domain.Response
import com.mylektop.simplecontact.listener.CallbackListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by MyLektop on 25/10/2019.
 */
class ContactRepository(
    private val serviceGET: ContactService.GET,
    private val servicePOST: ContactService.POST,
    private val serviceDELETE: ContactService.DELETE,
    private val servicePUT: ContactService.PUT
) {
    fun getContact(callbackListener: CallbackListener<GetContactResponse>): Disposable {
        return serviceGET.getContact()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> callbackListener.onCompleted(t) },
                { t -> callbackListener.onError(t) })
    }

    fun postContact(
        postContactBody: PostContactBody,
        callbackListener: CallbackListener<Response>
    ): Disposable {
        return servicePOST.postContact(postContactBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> callbackListener.onCompleted(t) },
                { t -> callbackListener.onError(t) })
    }

    fun putContact(
        id: String,
        postContactBody: PostContactBody,
        callbackListener: CallbackListener<PutContactResponse>
    ): Disposable {
        return servicePUT.putContact(id, postContactBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> callbackListener.onCompleted(t) },
                { t -> callbackListener.onError(t) })
    }

    fun deleteContact(
        id: String,
        callbackListener: CallbackListener<Response>
    ): Disposable {
        return serviceDELETE.deleteContact(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { t -> callbackListener.onCompleted(t) },
                { t -> callbackListener.onError(t) })
    }
}