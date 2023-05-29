package com.example.lapakkita_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityOnboardBinding
import com.example.lapakkita_android.ui.components.ButtonPrimary
import com.example.lapakkita_android.ui.components.ButtonSecondary

class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setContent {
            ButtonPrimary(
                onClicked = {
                    val intent = Intent(this@OnboardActivity, LoginActivity::class.java)
                    startActivity(intent)
                },
                text = resources.getString(R.string.login),
                isEnabled = true
            )
        }

        binding.registerBtn.setContent {
            ButtonSecondary(
                onClicked = {
                    val intent = Intent(this@OnboardActivity, RegisterActivity::class.java)
                    startActivity(intent)
                },
                text = resources.getString(R.string.register),
                isEnabled = true
            )
        }
    }
}