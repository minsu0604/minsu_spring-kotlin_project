package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForCategoryDetail
import com.example.myapplication.adapters.AdapterForSearch
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

class SearchFragment : Fragment() {

    private lateinit var binding:FragmentSearchBinding

    private lateinit var searchAdapter:AdapterForCategoryDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        val gridLayoutManager = GridLayoutManager(this.context, 5, GridLayoutManager.HORIZONTAL, false)
        val linearLayoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = AdapterForSearch()
        searchAdapter = AdapterForCategoryDetail((mutableListOf<ProjectDetail>()))


        val searchView = binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            // 검색 버튼 입력시 호출, 검색 버튼이 없으므로 사용하지 않는다.
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            // 텍스트 입력, 수정시 호출
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isEmpty()){
                    binding.recyclerView.layoutManager = gridLayoutManager
                    binding.recyclerView.adapter = AdapterForSearch()
                    binding.textView4.visibility = View.VISIBLE
                    return false
                }

                FunClient.retrofit.getProjectBySearchKey(newText.toString()).enqueue(object : Callback<List<ProjectDetail>>{
                    override fun onResponse(
                        call: Call<List<ProjectDetail>>,
                        response: Response<List<ProjectDetail>>
                    ) {
                        val result = response.body() as MutableList<ProjectDetail>
                        Log.d("getProjectBySearchKey", "result: ${response.body()}")
                        if(result.isEmpty()){
                            binding.recyclerView.adapter =AdapterForSearch()
                            binding.recyclerView.layoutManager = gridLayoutManager
                            binding.textView4.visibility = View.VISIBLE
                            return
                        }
                        binding.textView4.visibility = View.GONE
                        searchAdapter.projectList = result
//                        searchAdapter.favoriteList = result.stream().map{it -> it.projectId}.collect(Collectors.toList()).toMutableList()
                        val shared = context?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
                        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
                        if(isLoggedIn == true) {
                            loadFavorite()
                        }
                        binding.recyclerView.adapter = searchAdapter
                        binding.recyclerView.layoutManager = linearLayoutManager
                    }

                    override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                        Log.e("failed getProjectBySearchKey", "faild search key: ${t.message}", t)
                    }

                })
                return false
            }
        })

        binding.searchView.setOnSearchClickListener{
            Log.d("setOnSearchClickListener", "setOnSearchClickListener")
        }



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val shared = context?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn == true) {
            loadFavorite()
        }
    }

    fun loadFavorite(){
        var userId = ""
        val shared = context?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        var favoriteProjectIdList:MutableList<Int>
        userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "").toString()

        FunClient.retrofit.getFavoriteProject(userId).enqueue(object : retrofit2.Callback<List<ProjectDetail>>{
            override fun onResponse(
                call: Call<List<ProjectDetail>>,
                response: Response<List<ProjectDetail>>
            ) {
                val projectList = response.body() as MutableList<ProjectDetail>
                favoriteProjectIdList = projectList.stream().map { it.projectId }.collect(
                    Collectors.toList()).toMutableList()
                searchAdapter.favoriteList = favoriteProjectIdList;
                searchAdapter.userId = userId
                searchAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
            }

        })
    }

}