package com.example.dailerkotlin

import AppDatabase
import FavoritesFragment
import RecentsFragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dailerkotlin.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use dark theme if not already applied in styles.xml
        setTheme(R.style.Theme_DailerKotlin_Dark)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//load data base:
        LoadDatabase()
        // Setup Fragmen`ts
        val fragments = listOf(
            KeypadFragment(),
            RecentsFragment(),
            FavoritesFragment(),
            ContactsFragment()
        )

        val titles = listOf("Keypad", "Recents", "Favorites", "Contacts")
        val adapter = ViewPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        // Attach TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()

        // Optional: Set tab elevation or padding styling
        binding.tabLayout.setSelectedTabIndicatorColor(getColor(R.color.teal_200))
    }

    private fun LoadDatabase() {
        db = AppDatabase.getDatabase(this)
    }

}
