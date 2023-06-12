package com.example.lapakkita_android.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.entity.StoreEntity
import com.example.lapakkita_android.databinding.FragmentProfileBinding
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.EditProfilePhotoActivity
import com.example.lapakkita_android.ui.activity.OnboardActivity
import com.example.lapakkita_android.ui.components.BookmarkList
import com.example.lapakkita_android.ui.components.ProfileCard
import com.example.lapakkita_android.ui.components.ProfileTopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private val fakeData = mutableListOf(
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setupComponent()

        return binding.root
    }

    private fun setupComponent() {
        auth = Firebase.auth
        binding.profileTopBar.setContent{
            ProfileTopBar(
                onClick = {
                    auth.signOut()
                    val intent = Intent(requireContext(), OnboardActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    activity?.finish()
                }
            )
        }
        binding.profileCard.setContent {
            ProfileCard(
                editPhotoClick = {
                    startActivity(
                        Intent(
                            requireContext(),
                            EditProfilePhotoActivity::class.java
                        ),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(requireContext() as Activity).toBundle()
                    )
                }
            )
        }
        binding.bookmarkList.setContent {
            BookmarkList(
                listItem = fakeData,
                onClick = {
                    startActivity(
                        Intent(
                            requireContext(),
                            DetailActivity::class.java
                        )
                    )
                }
            )
        }
    }
}