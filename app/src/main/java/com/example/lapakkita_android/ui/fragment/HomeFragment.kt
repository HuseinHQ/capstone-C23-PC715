package com.example.lapakkita_android.ui.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.lapakkita_android.R
import com.example.lapakkita_android.databinding.FragmentHomeBinding
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.LoginActivity
import com.example.lapakkita_android.ui.components.BackButton
import com.example.lapakkita_android.ui.components.CloseButton
import com.example.lapakkita_android.ui.components.HomeTopBar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isShowDetail = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupMap()
        setupComponent()
        return binding.root
    }

    private fun setupComponent() {
        binding.btnClose.setContent {
            CloseButton(
                onClick = {
                    hideCardView()
                }
            )
        }

        binding.homeTopBar.setContent {
            HomeTopBar()
        }
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        map.setInfoWindowAdapter(null)
        val sydney = LatLng(
            -34.0,
            151.0
        )
        val marker = map.addMarker(MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney")
        )
        marker?.tag = "OK"

        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        map.setOnMapClickListener {
            if(isShowDetail) {
                hideCardView()
            }
        }
        map.setOnMarkerClickListener {
            it.hideInfoWindow()
            showCardView(it)
            false
        }
        map.uiSettings.isZoomGesturesEnabled = true
        map.uiSettings.isCompassEnabled = true

    }

    private fun showCardView(marker: Marker) {
        if(!isShowDetail) {
            isShowDetail = !isShowDetail
            ObjectAnimator.ofFloat(
                binding.mapDetail,
                View.TRANSLATION_Y,
                480f,
                -480f
            )
                .setDuration(500)
                .start()
            binding.detailName.text = marker.title
            binding.mapDetail.setOnClickListener {
                startActivity(
                    Intent(
                        context,
                        DetailActivity::class.java
                    )
                )
            }
        }
    }
    private fun hideCardView(){
        isShowDetail = !isShowDetail
        ObjectAnimator.ofFloat(
            binding.mapDetail,
            View.TRANSLATION_Y,
            -480f,
            480f
        )
            .setDuration(500)
            .start()
    }

}