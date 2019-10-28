package com.mylektop.simplecontact.listener

import android.view.View

/**
 * Created by MyLektop on 27/10/2019.
 */
interface RecyclerViewListener<T> {
    fun onItemChooseCallback(view: View, t: T, position: Int)
}