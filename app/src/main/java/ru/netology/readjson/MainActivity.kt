package ru.netology.readjson

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.readjson.databinding.ActivityMainBinding
import ru.netology.readjson.fragment.FilterFragment
import ru.netology.readjson.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(MainFragment.newInstance())
        bottomMenuClicks()
    }

    private fun bottomMenuClicks() {
        binding.bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map -> {
                    openFragment(MainFragment.newInstance())
                }
                R.id.filter -> {
                    openFragment(FilterFragment.newInstance())
                }
            }
            true
        }
    }

}