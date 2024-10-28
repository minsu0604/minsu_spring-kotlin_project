package com.example.myapplication.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForCategoryDetail
import com.example.myapplication.databinding.ActivityCategoryBinding
import com.example.myapplication.retrofitPacket.CategoryPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response
import java.util.stream.Collectors

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryDetailAdapter: AdapterForCategoryDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category = intent.getSerializableExtra("category") as? CategoryPacket
        supportActionBar?.show()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = category?.title

        val projectList = mutableListOf<ProjectDetail>()
        categoryDetailAdapter = AdapterForCategoryDetail(projectList)
        binding.recyclerView.adapter = categoryDetailAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager

        category?.let {
            if (it.categoryId == 0) {
                var currentPage = 0

                binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val totalItemCount = linearLayoutManager.itemCount
                        val lastVisibleItemPosition =
                            linearLayoutManager.findLastVisibleItemPosition()

                        if (lastVisibleItemPosition == totalItemCount - 1) {
                            currentPage++
                            loadMoreData(currentPage)
                        }
                    }
                })
                loadMoreData(currentPage)

            } else {
                FunClient.retrofit.getProjectByCategory(it.categoryId)
                    .enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                        override fun onResponse(
                            call: Call<List<ProjectDetail>>,
                            response: Response<List<ProjectDetail>>
                        ) {
                            response.body()?.let {
                                categoryDetailAdapter.projectList = it.toMutableList()
                                categoryDetailAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                            Log.e(
                                "categoryProjectFetch",
                                "category project list fetch failed ${t.message}",
                                t
                            )
                        }

                    })
            }
        }

        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn == true){
            loadFavorite()
        }

    }

    override fun onResume() {
        super.onResume()

        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn == true) {
            loadFavorite()
        }
    }


    fun loadFavorite(){
        var userId = ""
        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
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
                categoryDetailAdapter.favoriteList = favoriteProjectIdList;
                categoryDetailAdapter.userId = userId
                categoryDetailAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
            }

        })
    }

    fun loadMoreData(page: Int) {

        FunClient.retrofit.getScrollProject(page)
            .enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                override fun onResponse(
                    call: Call<List<ProjectDetail>>,
                    response: Response<List<ProjectDetail>>
                ) {
                    response.body()?.let { newData ->
                        categoryDetailAdapter.projectList.addAll(newData)
                        categoryDetailAdapter.notifyDataSetChanged()
                        Log.d("resultData", "${newData}")
                    }
                }

                override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                    Log.e("RetrofitError", "Failed to load more data: ${t.message}")
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

}