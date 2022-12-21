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
import com.example.chatapp.constants.Constants
import com.example.chatapp.ui.activities.MessageActivity
import com.example.chatapp.databinding.SingleViewRvBinding
import com.example.chatapp.model.UserModel

class ChatAdapter(var context: Context, var list: ArrayList<UserModel>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: SingleViewRvBinding = SingleViewRvBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.single_view_rv, parent, false))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val user = list[position]
        Glide.with(context).load(user.userImage).into(holder.binding.imgUser)
        holder.binding.tvUserName.text = user.userName
        holder.itemView.setOnClickListener {

            val intent=Intent(context, MessageActivity::class.java)
            intent.putExtra(Constants.UID,user.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}