package com.dev_yogesh.sampleapp.Ui.registerLogin.registerLogin

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dev_yogesh.sampleapp.R
import com.dev_yogesh.sampleapp.Ui.homeActivity.HomeActivity
import com.dev_yogesh.sampleapp.Ui.registerLogin.LoginActivity.LoginActivity
import com.dev_yogesh.sampleapp.databinding.ActivityMainBinding
import com.dev_yogesh.sampleapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.registerButtonRegister.setOnClickListener {

            performRegister()

        }


        binding.alreadyHaveAccountTextViewRegister.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }






        showLoading(false)
    }

    private fun showLoading(check:Boolean){
        if(check){
            binding.progressBarRegister.visibility = View.VISIBLE
            binding.mainlayoutRegister.visibility = View.GONE
        }else{
            binding.progressBarRegister.visibility = View.GONE
            binding.mainlayoutRegister.visibility = View.VISIBLE
        }

    }



    private fun performRegister() {

        val email = binding.emailEdittextRegister.text.toString()
        val password = binding.passwordEdittextRegister.text.toString()
        val userName = binding.usernameEditTextRegister.text.toString()
        Log.d("MainActivity", "Email is: $email" )
        Log.d("MainActivity", "Password: $password")

        if(email.isEmpty()||password.isEmpty()||userName.isEmpty()){

            Toast.makeText(this,getString(R.string.username_email_password_error),Toast.LENGTH_SHORT).show()

            return
        }


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                showLoading(true)
                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }.addOnFailureListener {
                showLoading(false)
                Toast.makeText(this,getString(R.string.register_error), Toast.LENGTH_SHORT).show()
            }
    }



}

