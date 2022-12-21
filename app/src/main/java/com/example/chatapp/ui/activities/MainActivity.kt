package com.example.chatapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding
import com.example.chatapp.fragmentUtils.FragmentUtils
import com.example.chatapp.ui.fragments.ChatFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(ChatFragment())
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
            finish()
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        FragmentUtils.changeFragment(R.id.Fragment, fragment, fragmentManager)
    }
}