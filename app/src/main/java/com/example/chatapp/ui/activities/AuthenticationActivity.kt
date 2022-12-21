package com.example.chatapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.constants.Constants
import com.example.chatapp.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthenticationBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
            finish()
        }
        binding.btnContinueNumber.setOnClickListener {

            if (binding.edtNumber.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_plz_enter_number),
                    Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this@AuthenticationActivity, OTPActivity::class.java)
                intent.putExtra(Constants.NUMBER, binding.edtNumber.text!!.toString())
                startActivity(intent)

            }
        }
    }
}