package com.example.chatapp.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chatapp.adapter.ChatAdapter
import com.example.chatapp.constants.Constants
import com.example.chatapp.databinding.FragmentChatBinding
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    var dataBase: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    lateinit var userList: ArrayList<UserModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance()
        userList = ArrayList()
        dataBase!!.reference.child(Constants.USERS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapShot1 in snapshot.children) {
                        val user = snapShot1.getValue(UserModel::class.java)
                        if (user?.uid != FirebaseAuth.getInstance().uid) {
                            user?.let { userList.add(it) }
                        }
                    }
                    binding.recyclerViewChat.adapter = ChatAdapter(requireContext(), userList)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        return binding.root


    }
}