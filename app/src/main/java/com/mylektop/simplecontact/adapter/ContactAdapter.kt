package com.mylektop.simplecontact.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mylektop.simplecontact.R
import com.mylektop.simplecontact.domain.Contact
import com.mylektop.simplecontact.listener.RecyclerViewListener

/**
 * Created by MyLektop on 27/10/2019.
 */
class ContactAdapter(private val list: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {

    var recyclerViewListener: RecyclerViewListener<Contact>? = null
    var buttonListener: ButtonListener? = null

    interface ButtonListener {
        fun onButtonItemClicked(view: View, contact: Contact, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        return ContactAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contact,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        val item = this.list[position]

        Glide
            .with(holder.imgPhoto.context)
            .load(item.photo)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.placeholder_rounded)
            .into(holder.imgPhoto)

        holder.txtName.text = item.firstName + " " + item.lastName
        holder.txtAge.text = holder.txtAge.resources.getString(R.string.msg_age) + " " + item.age

        holder.container.setOnClickListener {
            recyclerViewListener?.onItemChooseCallback(it, item, position)
        }

        holder.imgDelete.setOnClickListener {
            buttonListener?.onButtonItemClicked(it, item, position)
        }
    }

    class ContactAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container: LinearLayout = itemView.findViewById(R.id.ll_container_item)
        var imgPhoto: AppCompatImageView = itemView.findViewById(R.id.iv_photo)
        var txtName: AppCompatTextView = itemView.findViewById(R.id.tv_firstName_lastName)
        var txtAge: AppCompatTextView = itemView.findViewById(R.id.tv_age)
        var imgDelete: AppCompatImageView = itemView.findViewById(R.id.iv_delete)
    }
}