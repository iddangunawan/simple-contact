package com.mylektop.simplecontact.core

/**
 * Created by MyLektop on 23/10/2019.
 */
interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
    fun initView()
}