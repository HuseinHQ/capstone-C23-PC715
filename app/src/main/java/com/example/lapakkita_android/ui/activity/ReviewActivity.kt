package com.example.lapakkita_android.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.ReviewEntity
import com.example.lapakkita_android.databinding.ActivityReviewBinding
import com.example.lapakkita_android.ui.components.ButtonContainer
import com.example.lapakkita_android.ui.components.ListReview
import com.example.lapakkita_android.ui.components.TopBar

class ReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReviewBinding
    private val fakeData = listOf(
        ReviewEntity(
            id = 1,
            user = "user",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 4.5,
            description = "lorem"
        ),
        ReviewEntity(
            id = 1,
            user = "user",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 4.5,
            description = "lorem"
        ),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponent()
    }

    private fun setupComponent(){
        binding.reviewTopBar.setContent {
            TopBar(
                text = resources.getString(R.string.review),
                toBack = { finish() }
            )
        }
        binding.addReviewBtn.setContent {
            ButtonContainer(
                onClicked = {
                    startActivity(
                        Intent(
                            this@ReviewActivity,
                            AddReviewActivity::class.java
                        )
                    )
                },
                text = resources.getString(R.string.add_review)
            )
        }
        binding.rvReview.setContent {
            ListReview(listItem = fakeData)
        }
    }

    companion object{
        const val REVIEW_STORE_ID = "review_store_id"
    }
}