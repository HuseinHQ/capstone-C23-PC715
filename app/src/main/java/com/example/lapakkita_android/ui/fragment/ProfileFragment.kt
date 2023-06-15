package com.example.lapakkita_android.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.lapakkita_android.databinding.FragmentProfileBinding
import com.example.lapakkita_android.di.Injection
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.EditProfilePhotoActivity
import com.example.lapakkita_android.ui.activity.OnboardActivity
import com.example.lapakkita_android.ui.components.BookmarkScreen
import com.example.lapakkita_android.ui.components.ProfileCard
import com.example.lapakkita_android.ui.components.ProfileTopBar
import com.example.lapakkita_android.ui.viewmodel.BookmarkViewModel
import com.example.lapakkita_android.ui.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var bookmarkViewModel: BookmarkViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        setupComponent()

        val injection = Injection.provideStoreRepository(requireContext())
        bookmarkViewModel = ViewModelProvider(this, ViewModelFactory(injection))[BookmarkViewModel::class.java]
        binding.bookmarkList.setContent {
            BookmarkScreen(
                viewModel = bookmarkViewModel,
                onClick = {
                    startActivity(
                        Intent(
                            requireContext(),
                            DetailActivity::class.java
                        ).putExtra(
                            DetailActivity.STORE_ID, it
                        )
                    )
                },
            )
        }
        return binding.root
    }

    private fun setupComponent() {
        auth = Firebase.auth
        val user = auth.currentUser
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
            if (user != null) {
                ProfileCard(
                    user = user,
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
        }
    }
}