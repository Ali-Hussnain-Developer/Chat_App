package com.example.chatapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chatapp.R
import com.example.chatapp.constants.Constants
import com.example.chatapp.databinding.ActivityAuthenticationBinding
import com.example.chatapp.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpactivityBinding
    lateinit var auth: FirebaseAuth
    lateinit var dialog: AlertDialog
    lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)
        builder.setMessage(Constants.WAIT)
        builder.setMessage(Constants.LOADING)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
        val phoneNumber = Constants.COUNTRY_CODE + intent.getStringExtra(Constants.NUMBER)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(applicationContext,
                        getString(R.string.txt_match),
                        Toast.LENGTH_LONG)
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(applicationContext,
                        getString(R.string.txt_try_again),
                        Toast.LENGTH_LONG)
                        .show()
                    dialog.dismiss()
                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    dialog.dismiss()
                    verificationId = p0
                    super.onCodeSent(p0, p1)
                }

            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        binding.btnContinueOtp.setOnClickListener {

            if (binding.edtOTP.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_plz_enter_number),
                    Toast.LENGTH_LONG).show()
            } else {
                dialog.show()
                val credential =
                    PhoneAuthProvider.getCredential(verificationId, binding.edtOTP.text.toString())
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            dialog.dismiss()
                            startActivity(Intent(this, ProfileActivity::class.java))
                            finish()

                        } else {
                            dialog.dismiss()
                            Toast.makeText(applicationContext,
                                getString(R.string.txt_error),
                                Toast.LENGTH_LONG).show()
                        }
                    }

            }
        }

    }
}