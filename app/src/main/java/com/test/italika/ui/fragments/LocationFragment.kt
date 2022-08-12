package com.test.italika.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.test.italika.R
import com.test.italika.base.BaseFragment
import com.test.italika.databinding.FragmentLocationBinding
import com.test.italika.extensions.sendNotificationConpact
import com.test.italika.extensions.showSnackBar


class LocationFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentLocationBinding
    private lateinit var map: GoogleMap
    private lateinit var db: FirebaseFirestore
    private lateinit var fusedLocClient: FusedLocationProviderClient
    private val REQUEST_LOCATION = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        childFragmentManager.let {
            val mapFragment = it.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment!!.getMapAsync { this@LocationFragment }
        }
        getCurrentLocation()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LocationFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        readData()
    }

    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION
        )
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            requestLocPermissions()
        } else {
            fusedLocClient =
                LocationServices.getFusedLocationProviderClient(requireActivity())

            val mLocationRequest = LocationRequest.create()
            mLocationRequest.interval = 300000
            mLocationRequest.fastestInterval = 300000
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            val mLocationCallback: LocationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        if (location != null) {
                            val hashMap = hashMapOf<String, Any>(
                                "latitude" to location.latitude,
                                "longitude" to location.longitude,
                                "time" to location.time)
                            db.collection("locations")
                                .add(hashMap)
                                .addOnSuccessListener {
                                    requireActivity().sendNotificationConpact(
                                        "Localizacion Enviada",
                                        "La localizacion lat: ${location.latitude} lon: ${location.longitude} fue almacenada satisfactoriamente")
                                }
                                .addOnFailureListener { exception ->
                                    requireActivity().showSnackBar(
                                        exception.message!!,
                                        R.color.error_color)
                                }
                        }
                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(context!!)
                .requestLocationUpdates(mLocationRequest, mLocationCallback, null)
        }
    }

    private fun readData() {
        db.collection("locations")
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach {
                    var latitude: Double = it.data["latitude"] as Double
                    var longitude: Double = it.data["longitude"] as Double
                    val latLng = LatLng(latitude, longitude)
                    map.addMarker(
                        MarkerOptions().position(latLng)
                            .title("You are currently here!")
                    )
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                    map.moveCamera(update)
                }
            }
            .addOnFailureListener { exception ->
            }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }
}