package com.example.chatapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.ui.activities.MessageActivity
import com.example.chatapp.databinding.SingleViewRecieveItemBinding
import com.example.chatapp.databinding.SingleViewRvBinding
import com.example.chatapp.databinding.SingleViewSendItemBinding
import com.example.chatapp.model.MessageModel
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(var context: Context, var list: ArrayList<MessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var ITEM_SENT = 1
    var ITEM_RECIEVE = 2


    inner class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bindingSending: SingleViewSendItemBinding = SingleViewSendItemBinding.bind(view)
    }

    inner class RecieveViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bindingRecieve: SingleViewRecieveItemBinding = SingleViewRecieveItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SENT) {
            SentViewHolder(
                LayoutInflater.from(context).inflate(R.layout.single_view_send_item, parent, false))
        } else {
            RecieveViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.single_view_recieve_item, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SENT else ITEM_RECIEVE
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        if (holder.itemViewType == ITEM_SENT) {
            val viewHolder = holder as SentViewHolder
            viewHolder.bindingSending.tvSentMessage.text = message.message
        } else {
            val viewHolder = holder as RecieveViewHolder
            viewHolder.bindingRecieve.tvReceiveMessage.text = message.message
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}