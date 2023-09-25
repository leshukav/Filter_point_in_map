package ru.netology.readjson.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import ru.netology.readjson.JsonKotlin
import ru.netology.readjson.MainViewModel
import ru.netology.readjson.R
import ru.netology.readjson.databinding.FragmentMainBinding
import java.io.IOException

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsOsm()
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jsonFileString = getJsonDataFromAsset(requireContext(), "pins.json")

        val gson = Gson()
        val jsonKotlin = gson.fromJson(jsonFileString, JsonKotlin::class.java)

        model.listService.value = jsonKotlin.services
        model.jsonKotlin.value = jsonKotlin

        //   val listPersonType = object : TypeToken<JsonKotlin>() {}.type
        // val persons: JsonKotlin = gson.fromJson(jsonFileString, listPersonType)
        //      persons.pins.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }
        // GeoPoint   центр Москва
        goToStartPosition(GeoPoint(55.752957, 37.620174))
        setMarkers(GeoPoint(55.752957, 37.620174))

        model.listGeo.observe(viewLifecycleOwner){ listGeoPoint ->
           listGeoPoint.forEach {
                val startMarker = Marker(binding.mapView2)
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                startMarker.icon = getDrawable(requireContext(), R.drawable.ic_location)
                startMarker.position = GeoPoint(it.latitude, it.longitude)
                binding.mapView2.overlays.add(startMarker)
            }
        }
    }

    private fun settingsOsm() {
        Configuration.getInstance().load(
            (activity as AppCompatActivity),
            activity?.getSharedPreferences("osm_pref", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setMarkers(point: GeoPoint) = with(binding) {
        val startMarker = Marker(mapView2)
        startMarker.setAnchor(
            org.osmdroid.views.overlay.Marker.ANCHOR_CENTER,
            org.osmdroid.views.overlay.Marker.ANCHOR_BOTTOM
        )
        startMarker.icon = getDrawable(requireContext(), R.drawable.ic_location)
        startMarker.position = point
        mapView2.overlays.add(startMarker)

    }

    private fun goToStartPosition(startPosition: GeoPoint) {
        binding.mapView2.controller.setZoom(13.0)
        binding.mapView2.controller.animateTo(startPosition)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }


}