package com.example.lapakkita_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityLoginBinding
import com.example.lapakkita_android.ui.components.*

class LoginActivity : AppCompatActivity() {
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

        binding.backBtn.setContent {
            BackButton(
                onClick = {finish()}
            )
        }
    }
}