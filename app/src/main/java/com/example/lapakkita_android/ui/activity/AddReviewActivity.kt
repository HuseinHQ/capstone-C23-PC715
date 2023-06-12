package com.example.lapakkita_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityAddReviewBinding
import com.example.lapakkita_android.ui.components.ButtonContainer
import com.example.lapakkita_android.ui.components.ReviewTextField
import com.example.lapakkita_android.ui.components.TopBar

class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewBinding
    private var commentText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupComponent()
    }
    
    private fun setupComponent() {
        binding.reviewTopBar.setContent {
            TopBar(
                text = resources.getString(R.string.add_review),
                toBack = { finish() }
            )
        }
        binding.addReviewBtn.setContent {
            ButtonContainer(
                onClicked = { checkCommentIsEmpty() },
                text = resources.getString(R.string.send_review)
            )
        }

        binding.commentEdt.setContent {
            ReviewTextField(
                getCommentText = {
                    commentText = it
                }
            )
        }
    }

    private fun checkCommentIsEmpty() {
        if(binding.ratingbarReview.rating != 0.0f) {
            if(commentText.isNotEmpty()){
                val intent = Intent(
                    this@AddReviewActivity,
                    ReviewActivity::class.java
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                finish()
                startActivity(intent)
            }
            else{
                Toast.makeText(this, resources.getString(R.string.comment_empty), Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, resources.getString(R.string.rating_empty), Toast.LENGTH_SHORT).show()
        }
    }
}