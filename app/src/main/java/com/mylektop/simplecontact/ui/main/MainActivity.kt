package com.mylektop.simplecontact.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mylektop.simplecontact.R
import com.mylektop.simplecontact.adapter.ContactAdapter
import com.mylektop.simplecontact.domain.Contact
import com.mylektop.simplecontact.domain.PostContactBody
import com.mylektop.simplecontact.listener.RecyclerViewListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_contact.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private var shimmer: ShimmerFrameLayout? = null
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var contactId: String = ""

    override val presenter by inject<MainActivityContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        presenter.view = this
        presenter.start()
    }

    override fun onResume() {
        super.onResume()

        presenter.getContact()
    }

    override fun initView() {
        setSupportActionBar(toolbar)

        srl_main.setOnRefreshListener {
            presenter.getContact()

            srl_main.isRefreshing = false
        }

        rv_contact.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        bottomSheetBehavior = BottomSheetBehavior.from(bs_contact)

        bottomSheetBehavior?.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            override fun onStateChanged(p0: View, p1: Int) {
                when (p1) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        fab.hide()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fab.show()
                    }
                }
            }

            override fun onSlide(p0: View, p1: Float) {
            }
        })

        fab.setOnClickListener {
            if (bottomSheetBehavior?.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

                bs_contact_title.text = resources.getString(R.string.action_add_contact)
                bs_contact_btn.text = resources.getString(R.string.action_add_contact)

                tet_first_name.setText("")
                tet_last_name.setText("")
                tet_age.setText("")
                tet_url_photo.setText("")

                bs_contact_btn.setOnClickListener {
                    var valid = true

                    til_first_name.clearFocus()
                    til_first_name.isErrorEnabled = false
                    til_last_name.clearFocus()
                    til_last_name.isErrorEnabled = false
                    til_age.clearFocus()
                    til_age.isErrorEnabled = false
                    til_url_photo.clearFocus()
                    til_url_photo.isErrorEnabled = false

                    if (tet_first_name.text.toString() == "") {
                        til_first_name.requestFocus()
                        til_first_name.error =
                            resources.getString(R.string.msg_first_name) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_last_name.text.toString() == "") {
                        til_last_name.requestFocus()
                        til_last_name.error =
                            resources.getString(R.string.msg_last_name) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_age.text.toString() == "") {
                        til_age.requestFocus()
                        til_age.error =
                            resources.getString(R.string.msg_age) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_age.text.toString() == "0") {
                        til_age.requestFocus()
                        til_age.error =
                            resources.getString(R.string.msg_age) + " " + resources.getString(
                                R.string.msg_must_larger
                            )

                        valid = false
                    }

                    if (tet_url_photo.text.toString() == "") {
                        til_url_photo.requestFocus()
                        til_url_photo.error =
                            resources.getString(R.string.msg_url_photo) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (valid) {
                        val contact = PostContactBody()

                        contact.firstName = tet_first_name.text.toString()
                        contact.lastName = tet_last_name.text.toString()
                        contact.age = tet_age.text.toString().toInt()
                        contact.photo = tet_url_photo.text.toString()

                        presenter.postContact(contact)
                    }
                }

                bs_contact_btn_delete.visibility = View.GONE
            } else {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        bs_contact_close.setOnClickListener {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    override fun setContactRecyclerViewAdapter(list: List<Contact>) {
        val contactAdapter = ContactAdapter(list)

        contactAdapter.recyclerViewListener = object : RecyclerViewListener<Contact> {
            override fun onItemChooseCallback(view: View, t: Contact, position: Int) {
                contactId = t.id

                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

                bs_contact_title.text = resources.getString(R.string.action_edit_contact)
                bs_contact_btn.text = resources.getString(R.string.action_update_contact)

                tet_first_name?.setText(t.firstName)
                tet_last_name?.setText(t.lastName)
                tet_age?.setText(t.age.toString())
                tet_url_photo?.setText(t.photo)

                bs_contact_btn.setOnClickListener {
                    var valid = true

                    til_first_name.clearFocus()
                    til_first_name.isErrorEnabled = false
                    til_last_name.clearFocus()
                    til_last_name.isErrorEnabled = false
                    til_age.clearFocus()
                    til_age.isErrorEnabled = false
                    til_url_photo.clearFocus()
                    til_url_photo.isErrorEnabled = false

                    if (tet_first_name.text.toString() == "") {
                        til_first_name.requestFocus()
                        til_first_name.error =
                            resources.getString(R.string.msg_first_name) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_last_name.text.toString() == "") {
                        til_last_name.requestFocus()
                        til_last_name.error =
                            resources.getString(R.string.msg_last_name) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_age.text.toString() == "") {
                        til_age.requestFocus()
                        til_age.error =
                            resources.getString(R.string.msg_age) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (tet_age.text.toString() == "0") {
                        til_age.requestFocus()
                        til_age.error =
                            resources.getString(R.string.msg_age) + " " + resources.getString(
                                R.string.msg_must_larger
                            )

                        valid = false
                    }

                    if (tet_url_photo.text.toString() == "") {
                        til_url_photo.requestFocus()
                        til_url_photo.error =
                            resources.getString(R.string.msg_url_photo) + " " + resources.getString(
                                R.string.msg_is_required
                            )

                        valid = false
                    }

                    if (valid) {
                        val contact = PostContactBody()

                        contact.firstName = tet_first_name.text.toString()
                        contact.lastName = tet_last_name.text.toString()
                        contact.age = tet_age.text.toString().toInt()
                        contact.photo = tet_url_photo.text.toString()

                        presenter.putContact(t.id, contact)
                    }
                }

                bs_contact_btn_delete.visibility = View.VISIBLE
                bs_contact_btn_delete.setOnClickListener {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)

                    builder.setMessage(resources.getString(R.string.msg_confirm_delete))
                        .setPositiveButton(
                            resources.getString(R.string.action_yes)
                        ) { dialog, which ->
                            presenter.deleteContact(contactId)
                        }
                        .setNegativeButton(
                            resources.getString(R.string.action_no),
                            null
                        )
                        .show()
                }
            }
        }

        contactAdapter.buttonListener = object : ContactAdapter.ButtonListener {
            override fun onButtonItemClicked(view: View, contact: Contact, position: Int) {
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)

                builder.setMessage(resources.getString(R.string.msg_confirm_delete))
                    .setPositiveButton(
                        resources.getString(R.string.action_yes)
                    ) { dialog, which ->
                        presenter.deleteContact(contact.id)
                    }
                    .setNegativeButton(
                        resources.getString(R.string.action_no),
                        null
                    )
                    .show()
            }
        }

        if (rv_contact != null)
            rv_contact.adapter = contactAdapter
    }

    override fun showLoadingContact(isShow: Boolean) {
        if (isShow) {
            if (shimmer == null)
                shimmer = sfl_contact

            shimmer?.startShimmer()
        } else {
            dismissPlaceHolder()
        }
    }

    override fun showInfoContact(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
            .show()
    }

    override fun hideFormAndReloadData() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        presenter.getContact()
    }

    private fun dismissPlaceHolder() {
        if (shimmer != null) {
            shimmer?.stopShimmer()
            shimmer?.visibility = View.GONE
        }
    }
}