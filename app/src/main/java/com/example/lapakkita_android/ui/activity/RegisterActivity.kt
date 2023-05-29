package com.example.lapakkita_android.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.databinding.ActivityRegisterBinding
import com.example.lapakkita_android.ui.components.RegisterForm

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerForm.setContent {
            RegisterForm(
                loginIntent = {
                    val intent = Intent(
                        this@RegisterActivity,
                        LoginActivity::class.java
                    )
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}