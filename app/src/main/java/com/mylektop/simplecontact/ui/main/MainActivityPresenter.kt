package com.mylektop.simplecontact.ui.main

import com.mylektop.simplecontact.data.repository.ContactRepository
import com.mylektop.simplecontact.domain.GetContactResponse
import com.mylektop.simplecontact.domain.PostContactBody
import com.mylektop.simplecontact.domain.PutContactResponse
import com.mylektop.simplecontact.domain.Response
import com.mylektop.simplecontact.listener.CallbackListener

/**
 * Created by MyLektop on 25/10/2019.
 */
class MainActivityPresenter(private val contactRepository: ContactRepository) :
    MainActivityContract.Presenter {

    override lateinit var view: MainActivityContract.View

    override fun start() {
        view.initView()
    }

    override fun getContact() {
        view.showLoadingContact(true)
        view.setContactRecyclerViewAdapter(mutableListOf())

        contactRepository.getContact(object : CallbackListener<GetContactResponse> {
            override fun onCompleted() {
            }

            override fun onCompleted(t: GetContactResponse) {
                view.setContactRecyclerViewAdapter(t.data!!)

                if (t.data.isNullOrEmpty())
                    view.showInfoContact("Data not found ..")

                view.showLoadingContact(false)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()

                view.showInfoContact("Error, Something went wrong ..")
                view.showLoadingContact(false)
            }
        })
    }

    override fun postContact(postContactBody: PostContactBody) {
        view.showLoadingContact(true)

        contactRepository.postContact(postContactBody, object : CallbackListener<Response> {
            override fun onCompleted() {
            }

            override fun onCompleted(t: Response) {
                view.showInfoContact(t.message)
                view.hideFormAndReloadData()
                view.showLoadingContact(false)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()

                view.showInfoContact("Error, Something went wrong ..")
                view.showLoadingContact(false)
            }
        })
    }

    override fun putContact(id: String, postContactBody: PostContactBody) {
        view.showLoadingContact(true)

        contactRepository.putContact(
            id,
            postContactBody,
            object : CallbackListener<PutContactResponse> {
                override fun onCompleted() {
                }

                override fun onCompleted(t: PutContactResponse) {
                    view.showInfoContact(t.data?.firstName + " " + t.message)
                    view.hideFormAndReloadData()
                    view.showLoadingContact(false)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()

                    view.showInfoContact("Error, Something went wrong ..")
                    view.showLoadingContact(false)
                }
            })
    }

    override fun deleteContact(id: String) {
        view.showLoadingContact(true)

        contactRepository.deleteContact(id, object : CallbackListener<Response> {
            override fun onCompleted() {
            }

            override fun onCompleted(t: Response) {
                view.showInfoContact(t.message)
                view.hideFormAndReloadData()
                view.showLoadingContact(false)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()

                view.showInfoContact("Error, Something went wrong ..")
                view.showLoadingContact(false)
            }
        })
    }
}