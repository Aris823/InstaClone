package com.example.instaclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.instaclone.databinding.ActivityMainBinding
import com.example.instaclone.fragments.AddPostFragment
import com.example.instaclone.fragments.HomeFragment
import com.example.instaclone.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            HomeFragment()
        ).commit()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    moveToFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.nav_add_post -> {
                    moveToFragment(AddPostFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.nav_profile -> {
                    moveToFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
            }

            moveToFragment(HomeFragment())

            false
        }


    }

    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
}

