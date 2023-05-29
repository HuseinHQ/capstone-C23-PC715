package com.example.lapakkita_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityLoginBinding
import com.example.lapakkita_android.ui.components.EmailForm
import com.example.lapakkita_android.ui.components.LoginForm
import com.example.lapakkita_android.ui.components.PasswordForm

class LoginActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginForm.setContent {
            LoginForm(
                mainIntent = {
                    val intent = Intent(
                        this@LoginActivity, MainActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(
                        intent
                    )
                    finish()
                }
            )
        }

        binding.backBtn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.back_btn -> {finish()}
        }
    }
}