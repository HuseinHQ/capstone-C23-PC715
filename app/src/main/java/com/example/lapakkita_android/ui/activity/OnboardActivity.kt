package com.example.lapakkita_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityOnboardBinding
import com.example.lapakkita_android.ui.components.ButtonPrimary
import com.example.lapakkita_android.ui.components.ButtonSecondary
import com.example.lapakkita_android.ui.components.OnboardButton

class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeButton.setContent {
            OnboardButton(
                loginClick = {
                    startActivity(
                        Intent(
                            this@OnboardActivity, LoginActivity::class.java
                        )
                    )
                 },
                registerClick = {
                    startActivity(
                        Intent(
                            this@OnboardActivity, RegisterActivity::class.java
                        )
                    )
                }
            )
        }
    }
}