package ru.netology.readjson.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.osmdroid.util.GeoPoint
import ru.netology.readjson.MainViewModel
import ru.netology.readjson.adapter.ItemAdapter
import ru.netology.readjson.databinding.FragmentFilterBinding
import ru.netology.readjson.openFragment

class FilterFragment : Fragment(), ItemAdapter.Listener {

    lateinit var adapter: ItemAdapter
    lateinit var binding: FragmentFilterBinding
    private var listGeo = ArrayList<GeoPoint>()
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        model.listService.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRcView() {
        adapter = ItemAdapter(this)
        binding.rcView.layoutManager = LinearLayoutManager(requireContext())
        binding.rcView.adapter = adapter
    }

    companion object {

        @JvmStatic
        fun newInstance() = FilterFragment()
    }

    override fun onClick(services: String) {
        model.jsonKotlin.value?.pins?.forEach {
            if (it.service == services) {
                val lat = it.coordinates.lat
                val lng = it.coordinates.lng
                listGeo.add(GeoPoint(lat, lng))
            }
        }
        model.listGeo.value = listGeo
        openFragment(MainFragment.newInstance())
    }
}