package com.example.lapakkita_android.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.databinding.ActivityRecommendationBinding
import com.example.lapakkita_android.ui.components.RecommendationList
import com.example.lapakkita_android.ui.components.TopBar
import java.io.IOException
import java.util.*

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding
    private val fakeData = listOf(
        StoreEntity(
            id = 1,
            name = "LatteCoffee",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 3.5,
            lat = -3.9822934,
            lon = 122.5153179,
        ),
        StoreEntity(
            id = 1,
            name = "LatteCoffee",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 3.5,
            lat = -3.9822934,
            lon = 122.5153179,
        ),
        StoreEntity(
            id = 1,
            name = "LatteCoffee",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 3.5,
            lat = -3.9822934,
            lon = 122.5153179,
        ),
        StoreEntity(
            id = 1,
            name = "LatteCoffee",
            photoUrl = "https://i.pravatar.cc/300",
            rating = 3.5,
            lat = -3.9822934,
            lon = 122.5153179,
        ),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCategory = intent.extras?.getString(SELECTED_CATEGORY)
        binding.tvSelectedCategory.text = resources.getString(R.string.selected_category, selectedCategory)
        setupComponent()
    }

    private fun setupComponent() {

        binding.recommendationTopBar.setContent {
            TopBar(
                text = resources.getString(R.string.recommendation),
                toBack = { finish() }
            )
        }

        fakeData.forEach {
            val geocoder = Geocoder(this, Locale.getDefault())
            try {
                val list = geocoder.getFromLocation(
                    it.lat,
                    it.lon,
                    1)
                if (list != null && list.size != 0) {
                    it.location = list[0].subLocality + ", " + list[0].locality
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(
                    this,
                    resources.getString(R.string.close),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.recommendationList.setContent {
            RecommendationList(
                listItem = fakeData,
                onClick = {
                    val intent = Intent(this, DetailActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }

    companion object{
        const val SELECTED_CATEGORY = "selected_category"
    }
}