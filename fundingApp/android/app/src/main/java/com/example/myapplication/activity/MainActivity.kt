package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.adapters.AdapterForMain
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.Const
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var adapterForMain: AdapterForMain
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterForMain = AdapterForMain(this)
        binding.viewPager.adapter = adapterForMain

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.tab_home)
                1 -> tab.setIcon(R.drawable.tab_search)
                2 -> tab.setIcon(R.drawable.tab_favorite)
                else -> tab.setIcon(R.drawable.tab_my)
            }
        }.attach()


        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, MessageActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn) {
            binding.floatingActionButton.visibility = View.VISIBLE
        } else {
            binding.floatingActionButton.visibility = View.GONE
        }
    }
}