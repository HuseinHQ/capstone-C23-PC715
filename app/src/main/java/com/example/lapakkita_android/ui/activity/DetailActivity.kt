package com.example.lapakkita_android.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.databinding.ActivityDetailBinding
import com.example.lapakkita_android.di.Injection
import com.example.lapakkita_android.ui.components.*
import com.example.lapakkita_android.ui.viewmodel.BookmarkViewModel
import com.example.lapakkita_android.ui.viewmodel.DetailViewModel
import com.example.lapakkita_android.ui.viewmodel.ViewModelFactory
import com.example.lapakkita_android.utils.getLocationFromLatLng
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var bookmarkViewModel: BookmarkViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idStore = intent.extras?.getInt(STORE_ID)

        setupComponent()
        val storeInjection = Injection.provideStoreRepository(this)
        bookmarkViewModel = ViewModelProvider(this, ViewModelFactory(storeInjection))[BookmarkViewModel::class.java]
        detailViewModel = ViewModelProvider(this, ViewModelFactory(storeInjection))[DetailViewModel::class.java]
        lifecycleScope.launch {
            detailViewModel.uiState.collect{state ->
                when(state){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        if (idStore != null) {
                            detailViewModel.getDetailStore(idStore)
                        }
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMessage.visibility = View.GONE
                        updateUI(state.data)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateUI(data: StoreEntity) {
        binding.detailImage.setContent {
            DetailPhoto(
                imageUrl = data.photoUrl
            )
        }
        binding.findLocationBtn.setContent {
            ButtonContainer(
                onClicked = {
                    val label = "Warung Makan Meohai"
                    val uri = "geo:${data.lat},${data.lon}?q=${data.lat},${data.lon}(${data.name})"

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage("com.google.android.apps.maps") // Specify the package name of the maps application
                    startActivity(intent)
                },
                text = resources.getString(R.string.find_location)
            )
        }
        binding.reviewBar.setContent {
            ReviewBar(
                onClick = {
                    startActivity(
                        Intent(
                            this@DetailActivity,
                            ReviewActivity::class.java
                        ).putExtra(
                            ReviewActivity.REVIEW_STORE_ID, data.id
                        )
                    )
                }
            )
        }
        binding.iconDetail.setContent {
            DetailIcon(
                editBookmark = {
                    var location = ""
                    if(it) {
                        location = getLocationFromLatLng(data.lat, data.lon, this)
                        Toast.makeText(this, resources.getString(R.string.added_bookmark),Toast.LENGTH_SHORT)
                        .show()
                    }
                    else {
                        Toast.makeText(this, resources.getString(R.string.removed_bookmark),Toast.LENGTH_SHORT)
                        .show()
                    }
                    bookmarkViewModel.setBookmark(data, it, location)
                },
                onClickShare = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.share_content, data.name, data.lat, data.lon))
                    startActivity(Intent.createChooser(intent, resources.getString(R.string.share_via)))
                },
                enableStatus = data.isBookmarked
            )
        }

        binding.iconRating.visibility = View.VISIBLE
        binding.detailName.text = data.name
        binding.detailRating.text = data.avgRating.toString()
        binding.detailCountRating.text = resources.getString(R.string.count_rating, data.countRating)
        binding.detailDesc.text = data.desc

    }

    private fun setupComponent() {
        binding.backBtn.setContent {
            BackContainer(
                onClick = {finish()}
            )
        }
    }
    companion object{
        const val STORE_ID = "store_id"
    }
}