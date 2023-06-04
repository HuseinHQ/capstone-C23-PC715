package com.example.lapakkita_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityDetailBinding
import com.example.lapakkita_android.ui.components.ButtonContainer
import com.example.lapakkita_android.ui.components.ButtonPrimary
import com.example.lapakkita_android.ui.components.DetailPhoto

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponent()
    }

    private fun setupComponent() {
        binding.detailImage.setContent {
            DetailPhoto(
                imageResource = R.drawable.onboard_bg
            )
        }
        binding.findLocationBtn.setContent {
            ButtonContainer(
                onClick = {}
            )
        }
    }
}