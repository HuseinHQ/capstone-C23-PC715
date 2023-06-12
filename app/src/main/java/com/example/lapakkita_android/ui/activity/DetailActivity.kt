package com.example.lapakkita_android.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.ActivityDetailBinding
import com.example.lapakkita_android.ui.components.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val latitude = -3.9822934
    private val longitude = 122.5153179
    private val store = "Warung makan meohai"
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
                onClicked = {
                    val label = "Warung Makan Meohai"
                    val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps") // Specify the package name of the maps application
                    startActivity(intent)
                },
                text = resources.getString(R.string.find_location)
            )
        }
        binding.backBtn.setContent {
            BackContainer(
                onClick = {finish()}
            )
        }
        binding.reviewBar.setContent {
            ReviewBar(
                onClick = {
                    startActivity(
                        Intent(
                            this@DetailActivity,
                            ReviewActivity::class.java
                        )
                    )
                }
            )
        }
        binding.iconDetail.setContent {
            DetailIcon(
                editBookmark = {
                    Toast.makeText(
                        this,
                        if(it) resources.getString(R.string.added_bookmark)
                        else resources.getString(R.string.removed_bookmark),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                },
                onClickShare = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_content, store, latitude, longitude))
                    startActivity(Intent.createChooser(intent, resources.getString(R.string.share_via)))
                }
            )
        }
    }
}