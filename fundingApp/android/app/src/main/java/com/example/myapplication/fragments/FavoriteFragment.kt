package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.FavoriteAdapter
import com.example.myapplication.databinding.FragmentFavoriteBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        setupUI()

        return binding.root
    }

    private fun setupUI() {
        binding.buttonLogin.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        val shared =
            activity?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")

        if (isLoggedIn) {
            binding.buttonLogin.visibility = View.GONE

            FunClient.retrofit.getFavoriteProject(userId!!)
                .enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                    override fun onResponse(
                        call: Call<List<ProjectDetail>>,
                        response: Response<List<ProjectDetail>>
                    ) {
                        if (response.body().isNullOrEmpty()) {
                            binding.recyclerView.visibility = View.GONE
                            binding.frameLayout.visibility = View.VISIBLE
                        } else {
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.frameLayout.visibility = View.GONE
                            val favoriteProjects = response.body() as MutableList<ProjectDetail>

                            val adapter = FavoriteAdapter(favoriteProjects)
                            binding.recyclerView.adapter = adapter
                            binding.recyclerView.layoutManager = LinearLayoutManager(context)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                        Log.e("FavoriteFragment", "Failed to fetch favorite projects", t)
                        binding.recyclerView.visibility = View.GONE
                        binding.frameLayout.visibility = View.VISIBLE
                    }
                })
        } else {

            binding.buttonLogin.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}