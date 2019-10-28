package com.mylektop.simplecontact.ui.main

import com.mylektop.simplecontact.core.BasePresenter
import com.mylektop.simplecontact.core.BaseView
import com.mylektop.simplecontact.domain.Contact
import com.mylektop.simplecontact.domain.PostContactBody

/**
 * Created by MyLektop on 25/10/2019.
 */
interface MainActivityContract {

    interface View : BaseView<Presenter> {
        fun setContactRecyclerViewAdapter(list: List<Contact>)
        fun showLoadingContact(isShow: Boolean)
        fun showInfoContact(msg: String)
        fun hideFormAndReloadData()
    }

    interface Presenter : BasePresenter<View> {
        fun getContact()
        fun postContact(postContactBody: PostContactBody)
        fun putContact(id: String, postContactBody: PostContactBody)
        fun deleteContact(id: String)
    }
}