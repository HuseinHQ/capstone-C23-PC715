package com.example.lapakkita_android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.databinding.ActivityRecommendationBinding
import com.example.lapakkita_android.di.Injection
import com.example.lapakkita_android.ui.components.RecommendationList
import com.example.lapakkita_android.ui.components.TopBar
import com.example.lapakkita_android.ui.fragment.ExploreFragment
import com.example.lapakkita_android.ui.viewmodel.ExploreViewModel
import com.example.lapakkita_android.ui.viewmodel.ExploreViewModelFactory
import com.example.lapakkita_android.ui.viewmodel.RecommendationViewModel
import com.example.lapakkita_android.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class RecommendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var recommendationViewModel: RecommendationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val selectedCategory = intent.extras?.getString(SELECTED_CATEGORY)
        val storeInjection = Injection.provideStoreRepository(this)
        recommendationViewModel = ViewModelProvider(this, ViewModelFactory(storeInjection))[RecommendationViewModel::class.java]
        lifecycleScope.launch {
            if (selectedCategory != null) {
                recommendationViewModel.getRecommendation(selectedCategory)
                recommendationViewModel.uiState.collect{state ->
                    when(state){
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorMessage.visibility = View.GONE
                            if(state.data.isNotEmpty()){
                                updateUI(state.data, selectedCategory)
                            }
                            else{
                                binding.errorMessage.visibility = View.VISIBLE
                            }
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                            if (state.error == NOT_FOUND) {
                                binding.errorMessage.text = resources.getString(R.string.not_found)
                            }
                            else{
                                binding.errorMessage.text = resources.getString(R.string.connection_error)
                            }
                        }
                    }
                }
            }
        }
        setupComponent()
    }

    private fun updateUI(
        data: List<StoreEntity>,
        selectedCategory: String
    ) {
        binding.recommendationList.setContent {
            RecommendationList(
                selectedCategory = selectedCategory,
                listItem = data,
                onClick = {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.STORE_ID, it)
                    startActivity(intent)
                },
                context = applicationContext
            )
        }
    }

    private fun setupComponent() {

        binding.recommendationTopBar.setContent {
            TopBar(
                text = resources.getString(R.string.recommendation),
                toBack = { finish() }
            )
        }
    }

    companion object{
        const val SELECTED_CATEGORY = "selected_category"
        const val NOT_FOUND = "not_found"
    }
}