package com.example.chatapp.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chatapp.R
import com.example.chatapp.constants.Constants
import com.example.chatapp.databinding.ActivityProfileBinding
import com.example.chatapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var fireBaseDataBase: FirebaseDatabase
    lateinit var fireBaseStorage: FirebaseStorage
    lateinit var selectedImage: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        fireBaseDataBase = FirebaseDatabase.getInstance()
        fireBaseStorage = FirebaseStorage.getInstance()
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(Constants.UPDATING_PROFILE)
        dialog.setCancelable(false)
        binding.imgUser.setOnClickListener {

            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = Constants.IMAGE_EXTENSION
            startActivityForResult(intent, 1)
        }

        binding.btnContinueProfile.setOnClickListener {
            if (binding.edtEnterName.text.isEmpty()) {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_plz_enter_name),
                    Toast.LENGTH_LONG).show()
            } else if (selectedImage == null) {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_select_pic),
                    Toast.LENGTH_LONG).show()
            } else {
                uploadData()
            } } }
    private fun uploadData() {
        val reference =
            fireBaseStorage.reference.child(Constants.PROFILE).child(Date().time.toString())
        reference.putFile(selectedImage).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                } } } }
    private fun uploadInfo(imgUrl: String) {
        val user = UserModel(auth.uid.toString(),
            imgUrl,
            binding.edtEnterName.text.toString(),
            auth.currentUser?.phoneNumber.toString())
        fireBaseDataBase.reference.child(Constants.USERS)
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                Toast.makeText(applicationContext,
                    getString(R.string.txt_data_inserted),
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null) {
            if (data.data != null) {
                selectedImage = data.data!!
                binding.imgUser.setImageURI(selectedImage)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}