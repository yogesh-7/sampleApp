package com.dev_yogesh.sampleapp.Ui.registerLogin.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dev_yogesh.sampleapp.R
import com.dev_yogesh.sampleapp.Ui.homeActivity.HomeActivity
import com.dev_yogesh.sampleapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButtonLogin.setOnClickListener {
            perfromLogin()
        }

        binding.backToRegisterLogin.setOnClickListener {
            finish()
        }
        showLoading(false)
    }

    private fun showLoading(check:Boolean){
        if(check){
            binding.progressBarLogin.visibility = View.VISIBLE
            binding.mainlayoutLogin.visibility = View.GONE
        }else{
            binding.progressBarLogin.visibility = View.GONE
            binding.mainlayoutLogin.visibility = View.VISIBLE
        }

    }

    private fun perfromLogin() {
        showLoading(true)
        val email = binding.emailEdittextLogin.text.toString()
        val password = binding.passwordEdittextLogin.text.toString()
        Log.d("LoginActivity", "Email is: $email")
        Log.d("LoginActivity", "Password: $password")

        if (email.isEmpty() || password.isEmpty()) {
            showLoading(false)
            Toast.makeText(this, getString(R.string.email_password_error), Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                showLoading(true)
                val intent = Intent(applicationContext, HomeActivity::class.java)
                 intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                 startActivity(intent)

            }.addOnFailureListener {
                showLoading(false)
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
            }
    }
}