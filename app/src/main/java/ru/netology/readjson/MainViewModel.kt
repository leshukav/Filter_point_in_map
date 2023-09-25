package ru.netology.readjson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.osmdroid.util.GeoPoint

class MainViewModel : ViewModel() {
    val listService = MutableLiveData<List<String>>()
    val jsonKotlin = MutableLiveData<JsonKotlin>()
    val listGeo = MutableLiveData<ArrayList<GeoPoint>>()
}