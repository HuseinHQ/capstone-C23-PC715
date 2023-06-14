package com.example.lapakkita_android.ui.fragment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.HistoryEntity
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.databinding.FragmentExploreBinding
import com.example.lapakkita_android.di.Injection
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.RecommendationActivity
import com.example.lapakkita_android.ui.components.*
import com.example.lapakkita_android.ui.viewmodel.ExploreViewModel
import com.example.lapakkita_android.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.ui.viewmodel.ExploreViewModelFactory


class ExploreFragment : Fragment() {
    private var _binding : FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var exploreViewModel: ExploreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(binding.dialogFilterSearch.visibility == View.GONE
                    && binding.dialogRecommendation.visibility == View.GONE){
                    requireActivity().findNavController(R.id.nav_host_fragment)
                        .popBackStack()
                }
                else{
                    hideFilterDialog()
                    hideRecommendationDialog()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupComponent()

        binding.dialogFilterSearch.visibility = View.GONE
        binding.dialogRecommendation.visibility = View.GONE

        val storeInjection = Injection.provideStoreRepository(requireContext())
        val historyInjection = Injection.provideHistoryRepository(requireContext())
        exploreViewModel = ViewModelProvider(this, ExploreViewModelFactory(storeInjection, historyInjection))[ExploreViewModel::class.java]
        lifecycleScope.launch {
            exploreViewModel.getListStore()
            exploreViewModel.uiState.collect{state ->
                when(state){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorMessage.visibility = View.GONE
                        if(state.data.isNotEmpty()){
                            updateUI(state.data)
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
        return binding.root
    }

    private fun updateUI(data: List<StoreEntity>) {
        binding.rvExplore.setContent {
            GridListStore(
                listItem = data,
                toDetail = { idStore ->
                    startActivity(
                        Intent(
                            requireContext(), DetailActivity::class.java
                        ).putExtra(
                            DetailActivity.STORE_ID, idStore
                        )
                    )
                },
                context = requireContext()
            )
        }
    }

    private fun setupComponent() {
        binding.recommendationBtn.setContent {
            RecommendationButton(
                onClick = {
                    showRecommendationDialog()
                }
            )
        }
        binding.searchBarEdt.setContent {
            SearchBar(
                isOpened = {
                    if(it){
                        binding.rvSearchResult.visibility = View.VISIBLE
                        binding.recommendationBtn.visibility = View.GONE
                    }
                    else{
                        binding.rvSearchResult.visibility = View.GONE
                        binding.recommendationBtn.visibility = View.VISIBLE
                    }
                },
                showDialog = { showFilterDialog() },
                onSearch = { query ->
                    exploreViewModel.searchStore(query)
                },
                viewModel = exploreViewModel
            )
        }

        binding.dialogFilterSearch.setContent {
            FilterSearch(
                hideDialog = {hideFilterDialog()},
                buttonClicked = { category, name ->
                    binding.dialogFilterSearch.visibility = View.GONE
                    exploreViewModel.searchStoreByCategoryAndName(category, name)
                }
            )
        }
        binding.dialogRecommendation.setContent {
            RecommendationSearch(
                hideDialog = {hideRecommendationDialog()},
                buttonClicked = {
                    val intent = Intent(requireContext(), RecommendationActivity::class.java)
                    intent.putExtra(RecommendationActivity.SELECTED_CATEGORY, it)
                    startActivity(intent)
                },
            )
        }
    }

    private fun showRecommendationDialog(){
        val animator = ObjectAnimator.ofFloat(
            binding.dialogRecommendation,
            View.ALPHA,
            0.0f,
            1.0f,
        )
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {
                binding.dialogRecommendation.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.duration = 300
        animator.start()
    }
    private fun showFilterDialog(){
        val animator = ObjectAnimator.ofFloat(
            binding.dialogFilterSearch,
            View.ALPHA,
            0.0f,
            1.0f,
        )
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {
                binding.dialogFilterSearch.visibility = View.VISIBLE
            }
            override fun onAnimationEnd(p0: Animator) {}
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.duration = 300
        animator.start()
    }
    private fun hideRecommendationDialog(){
        val animator = ObjectAnimator.ofFloat(
            binding.dialogRecommendation,
            View.ALPHA,
            1.0f,
            0.0f,
        )
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                binding.dialogRecommendation.visibility = View.GONE
            }
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.duration = 300
        animator.start()
    }
    private fun hideFilterDialog(){
        val animator = ObjectAnimator.ofFloat(
            binding.dialogFilterSearch,
            View.ALPHA,
            1.0f,
            0.0f,
        )
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator) {}
            override fun onAnimationEnd(p0: Animator) {
                binding.dialogFilterSearch.visibility = View.GONE
            }
            override fun onAnimationCancel(p0: Animator) {}
            override fun onAnimationRepeat(p0: Animator) {}

        })
        animator.duration = 300
        animator.start()
    }

    companion object{
        const val NOT_FOUND = "not_found"
    }
}