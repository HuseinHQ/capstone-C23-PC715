package com.example.lapakkita_android.ui.fragment

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.HistoryEntity
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.databinding.FragmentExploreBinding
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.RecommendationActivity
import com.example.lapakkita_android.ui.components.*
import java.io.IOException
import java.util.*

class ExploreFragment : Fragment() {
    private var _binding : FragmentExploreBinding? = null
    private val binding get() = _binding!!
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fakeData.forEach {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
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
                    context,
                    requireActivity().resources.getString(R.string.connection_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.rvExplore.setContent {
            GridListStore(
                listItem = fakeData,
                toDetail = {
                    startActivity(
                        Intent(
                            requireContext(), DetailActivity::class.java
                        )
                    )
                }
            )
        }
    }

    private fun setupComponent() {
        binding.searchBarEdt.setContent {
            SearchBar(
                isOpened = {
                    binding.rvSearchResult.visibility = if (it)View.VISIBLE
                    else View.GONE
                },
                showDialog = {showFilterDialog()},
                listItem = listOf(HistoryEntity(1,"OK"), HistoryEntity(1,"OK")),
                onDeleteClick = {},
            )
        }
        binding.recommendationBtn.setContent {
            RecommendationButton(
                onClick = {
                    showRecommendationDialog()
                }
            )
        }

        binding.dialogFilterSearch.setContent {
            FilterSearch(
                hideDialog = {hideFilterDialog()},
                buttonClicked = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                },
            )
        }
        binding.dialogRecommendation.setContent {
            RecommendationSearch(
                hideDialog = {hideRecommendationDialog()},
                buttonClicked = {
//                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
}