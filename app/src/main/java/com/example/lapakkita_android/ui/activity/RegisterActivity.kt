package com.example.lapakkita_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityRegisterBinding
import com.example.lapakkita_android.ui.components.RegisterForm

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerForm.setContent {
            RegisterForm()
        }
    }
}