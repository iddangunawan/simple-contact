package com.mylektop.simplecontact.listener

/**
 * Created by MyLektop on 23/10/2019.
 */
interface CallbackListener<T> {
    fun onCompleted()
    fun onCompleted(t: T)
    fun onError(e: Throwable)
}