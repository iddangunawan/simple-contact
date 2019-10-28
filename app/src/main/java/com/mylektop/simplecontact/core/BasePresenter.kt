package com.mylektop.simplecontact.core

/**
 * Created by MyLektop on 23/10/2019.
 */
interface BasePresenter<T> {
    fun start()
    var view: T
}