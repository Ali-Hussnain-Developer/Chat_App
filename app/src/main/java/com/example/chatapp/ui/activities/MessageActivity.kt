package com.example.chatapp.ui.activities


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.adapter.MessageAdapter
import com.example.chatapp.constants.Constants
import com.example.chatapp.databinding.ActivityMessageBinding
import com.example.chatapp.model.MessageModel
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import java.util.Date

class MessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMessageBinding
    lateinit var dataBase: FirebaseDatabase
    lateinit var senderUid: String
    lateinit var recieverUid: String
    lateinit var senderRoom: String
    lateinit var recieverRoom: String
    lateinit var messageList: ArrayList<MessageModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        senderUid = FirebaseAuth.getInstance().uid.toString()
        recieverUid = intent.getStringExtra(Constants.UID).toString()
        senderRoom = senderUid + recieverUid
        recieverRoom = recieverUid + senderUid
        dataBase = FirebaseDatabase.getInstance()
        messageList = ArrayList()
        binding.imgSendBtn.setOnClickListener {

            if (binding.edtSendMsg.text?.isEmpty()!!) {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_plz_enter_number),
                    Toast.LENGTH_LONG).show()
            } else {
                val message =
                    MessageModel(binding.edtSendMsg.text.toString(), senderUid, Date().time)
                val randomKey = dataBase.reference.push().key
                dataBase.reference.child(Constants.CHATS)
                    .child(senderRoom).child(Constants.MESSAGE).child(randomKey!!)
                    .setValue(message).addOnSuccessListener {
                        dataBase.reference.child(Constants.CHATS).child(recieverRoom)
                            .child(Constants.MESSAGE)
                            .child(randomKey)
                            .setValue(message).addOnSuccessListener {
                                binding.edtSendMsg.text = null
                                Toast.makeText(applicationContext,
                                    getString(R.string.txt_msg_sent),
                                    Toast.LENGTH_LONG).show()
                            }
                    }
            }
        }
        dataBase.reference.child(Constants.CHATS).child(senderRoom).child(Constants.MESSAGE)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (snapShot1 in snapshot.children) {
                        val message = snapShot1.getValue(MessageModel::class.java)
                        message?.let { messageList.add(it) }

                    }
                    binding.rvMessages.adapter = MessageAdapter(applicationContext, messageList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,
                        getString(R.string.txt_error),
                        Toast.LENGTH_LONG).show()
                }

            })

    }
}