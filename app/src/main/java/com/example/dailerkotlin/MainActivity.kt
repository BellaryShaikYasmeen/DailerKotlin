package com.example.dailerkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dailerkotlin.KeypadFragment
import com.example.dailerkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            KeypadFragment(),
            RecentsFragment(),
            FavoritesFragment(),
            ContactsFragment()
        )

        val titles = listOf("Keypad", "Recents", "Favorites", "Contacts")
        val adapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}