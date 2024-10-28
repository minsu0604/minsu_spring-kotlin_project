package com.example.myapplication.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForAll
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.adapters.AdapterForCategory
import com.example.myapplication.adapters.AdapterForProduct
import com.example.myapplication.adapters.AdapterForProductHoriz
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.retrofitPacket.CategoryPacket
import com.example.myapplication.retrofitPacket.HomeInitPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.UserPacket
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bannerView: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var imageSliderAdapter: AdapterForBanner
    private lateinit var categoryAdapter: AdapterForCategory
    private lateinit var populAdapter: AdapterForProduct
    private lateinit var deadlineAdapter: AdapterForProductHoriz
    private lateinit var allAdapter: AdapterForAll

    var currentPage = 0

    var itemLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FunClient.retrofit.getHomeInitData().enqueue(object : retrofit2.Callback<HomeInitPacket> {
            override fun onResponse(
                call: Call<HomeInitPacket>,
                response: Response<HomeInitPacket>
            ) {
                response.body()?.let { data ->
                    val bannerDataList: List<String> = data.bannerUrl
                    imageSliderAdapter.imageList = bannerDataList
                    imageSliderAdapter.notifyDataSetChanged()
                    bannerView.setCurrentItem(1, false)

                    val categoryList: List<CategoryPacket> = data.categorys
                    categoryAdapter.categoryList = categoryList
                    categoryAdapter.notifyDataSetChanged()

                    val popularProjectList: List<ProjectDetail> = data.popularProjects
                    populAdapter.projectList = popularProjectList
                    populAdapter.notifyDataSetChanged()

                    val deadlineProjectList: List<ProjectDetail> = data.deadlineProjects
                    deadlineAdapter.projectList = deadlineProjectList
                    deadlineAdapter.notifyDataSetChanged()

                    val scrollProjectList: MutableList<ProjectDetail> =
                        data.scrollProjects.toMutableList()
                    allAdapter.projectList = scrollProjectList
                    allAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<HomeInitPacket>, t: Throwable) {
                Log.e("home data fetch failed", "Failed to fetch data: ${t.message}", t)
            }

        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        bannerView = binding.bannerView
        val imageList = listOf<String>()
        imageSliderAdapter = AdapterForBanner(imageList)
        bannerView.adapter = imageSliderAdapter

        setupAutoSlide()

        val categoryView = binding.categoryRecyclerView
        val categoryList = listOf<CategoryPacket>(CategoryPacket(1, "없음"))
        categoryAdapter = AdapterForCategory(categoryList)
        categoryView.adapter = categoryAdapter
        categoryView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

//         인기순
        val populView = binding.populRecyclerView
        val productList = listOf<ProjectDetail>()
        populAdapter = AdapterForProduct(productList)
        populView.adapter = populAdapter
        populView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)

        // 마감순
        val productHorizView = binding.productRecycleViewHoriental
        val deadlineList = listOf<ProjectDetail>()
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        productHorizView.layoutManager = gridLayoutManager
        deadlineAdapter = AdapterForProductHoriz(deadlineList)
        productHorizView.adapter = deadlineAdapter

        // 전체
        val productAllView = binding.allProductRecyclerView
        val gridLayoutManager2 = GridLayoutManager(this.context, 2)
        val productAllList = mutableListOf<ProjectDetail>()
        productAllView.layoutManager = gridLayoutManager2
        allAdapter = AdapterForAll(productAllList)
        productAllView.adapter = allAdapter

        binding.homeScrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {

                if(itemLoading){
                    return
                }
                val lastChild = binding.homeScrollView.getChildAt(binding.homeScrollView.childCount - 1)

                val homeScrollHeight = binding.homeScrollView.height
                val homeScrollY = binding.homeScrollView.scrollY
                val diff = lastChild.bottom - (homeScrollHeight + homeScrollY )

                Log.d("Scrolling", "$diff ${homeScrollHeight} ${homeScrollY}")

                if (diff <= 25) {
                    itemLoading = true
                    currentPage++
                    loadMoreData(currentPage)
                }
            }
        })

        return binding.root
    }

    fun loadMoreData(page: Int) {

        FunClient.retrofit.getScrollProject(page)
            .enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                override fun onResponse(
                    call: Call<List<ProjectDetail>>,
                    response: Response<List<ProjectDetail>>
                ) {
                    response.body()?.let { newData ->
                        allAdapter.projectList.addAll(newData)
                        allAdapter.notifyDataSetChanged()
                        Log.d("resultData", "${newData}")
                    }
                    itemLoading = false
                }

                override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                    Log.e("RetrofitError", "Failed to load more data: ${t.message}")
                    itemLoading = false
                }
            })
    }

    private fun setupAutoSlide() {

        bannerView.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 0) {
                    handler.postDelayed({
                        bannerView.setCurrentItem(imageSliderAdapter.itemCount - 2, false)  // 실제 마지막 페이지로 이동
                    }, 300)
                }
                else if (position == imageSliderAdapter.itemCount - 1) {
                    handler.postDelayed({
                        bannerView.setCurrentItem(1, false)  // 실제 첫 번째 페이지로 이동
                    }, 300)
                }
            }
        })

        val runnable = object : Runnable {
            override fun run() {
                val nextItem = bannerView.currentItem + 1
                bannerView.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)

    }


}