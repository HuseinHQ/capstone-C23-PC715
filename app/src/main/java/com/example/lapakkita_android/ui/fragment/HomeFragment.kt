package com.example.lapakkita_android.ui.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lapakkita_android.R
import com.example.lapakkita_android.data.local.Result
import com.example.lapakkita_android.databinding.FragmentHomeBinding
import com.example.lapakkita_android.di.Injection
import com.example.lapakkita_android.ui.activity.DetailActivity
import com.example.lapakkita_android.ui.activity.LoginActivity
import com.example.lapakkita_android.ui.components.*
import com.example.lapakkita_android.ui.viewmodel.BookmarkViewModel
import com.example.lapakkita_android.ui.viewmodel.MapViewModel
import com.example.lapakkita_android.ui.viewmodel.ViewModelFactory
import com.example.lapakkita_android.utils.getLocationFromLatLng
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), OnMapReadyCallback {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapViewModel: MapViewModel
    private var isShowDetail = false
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupMap()
        setupComponent()

        val injection = Injection.provideStoreRepository(requireContext())
        mapViewModel = ViewModelProvider(this, ViewModelFactory(injection))[MapViewModel::class.java]
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
        mMap = map
        map.setInfoWindowAdapter(null)
        lifecycleScope.launch {
            mapViewModel.getAllMarker()
            mapViewModel.uiState.collect{state ->
                when(state){
                    is Result.Loading -> {}
                    is Result.Success -> {
                        if(state.data.isNotEmpty()){
                            state.data.forEach { data ->
                                map.addMarker(MarkerOptions()
                                    .position(LatLng(data.lat, data.lon))
                                    .title(data.name)
                                )?.tag = data.id
                            }
                        }
                        else{
                            Toast.makeText(requireContext(), resources.getString(R.string.no_data), Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), resources.getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
        getMyLocation()
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e("ERR", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("ERR", "Can't find style. Error: ", exception)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 15f))
            }
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showCardView(marker: Marker) {
        if(!isShowDetail) {
            binding.iconRating.visibility = View.GONE
            isShowDetail = !isShowDetail
            ObjectAnimator.ofFloat(
                binding.mapDetail,
                View.TRANSLATION_Y,
                480f,
                -480f
            )
                .setDuration(500)
                .start()
        }
        lifecycleScope.launch {
            mapViewModel.onClickMarker(marker.tag as Int)
            mapViewModel.uiStateMarker.collect{state ->
                when(state){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        state.data.let { store ->
                            binding.detailName.text = store.name
                            binding.mapDetail.setOnClickListener {
                                startActivity(
                                    Intent(
                                        context,
                                        DetailActivity::class.java
                                    ).putExtra(
                                        DetailActivity.STORE_ID, store.id
                                    )
                                )
                            }
                            binding.detailRating.text = store.avgRating.toString()
                            binding.iconRating.visibility = View.VISIBLE
                            Glide
                                .with(requireContext())
                                .load(store.photoUrl)
                                .placeholder(R.drawable.placeholder_image)
                                .into(binding.detailImage)
                            binding.detailLocation.text = getLocationFromLatLng(store.lat, store.lon, requireContext())
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(context, resources.getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                        hideCardView()
                    }
                }
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