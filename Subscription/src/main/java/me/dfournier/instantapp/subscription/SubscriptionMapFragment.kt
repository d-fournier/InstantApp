package me.dfournier.instantapp.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_subscribe_map.*


class SubscriptionMapFragment : BaseFragment() {


    private val markerOptions: Array<MarkerOptions> by lazy {
        arrayOf(
            MarkerOptions().position(LatLng(45.7604306, 4.8596415)).title("Part Dieu Station").icon(getColor()),
            MarkerOptions().position(LatLng(45.7579522, 4.8341221)).title("Bellecour Square").icon(getColor()),
            MarkerOptions().position(LatLng(45.7785792, 4.8514310)).title("Tete d'Or Park").icon(getColor())
        )
    }
    private val markers = mutableListOf<Marker>()
    private var selectedMarker: Marker? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribe_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map.onCreate(savedInstanceState)

        next.setOnClickListener {
            goToNextStep(this::class)
        }

        map.getMapAsync { maps ->
            markers.addAll(
                markerOptions.map { maps.addMarker(it) }
            )
            maps.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(45.7684698, 4.8443714), 14f))
            maps.setOnMarkerClickListener {
                selectedMarker?.setIcon(getColor())
                selectedMarker = it
                selectedMarker?.setIcon(getColor(BitmapDescriptorFactory.HUE_AZURE))
                selected_agency_text.text = getString(R.string.subscription_agency, it.title)
                next.isEnabled = true
                true
            }
        }
    }

    private fun getColor(color: Float = BitmapDescriptorFactory.HUE_RED) = BitmapDescriptorFactory.defaultMarker(color)

    override fun onStart() {
        super.onStart()
        map.onStart()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onStop() {
        super.onStop()
        map.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        map.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map.onLowMemory()
    }
}