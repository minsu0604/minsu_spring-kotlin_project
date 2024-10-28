package com.example.myapplication.Retrofit.test

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivityRetrofitTestBinding
import com.example.myapplication.dto.Category
import com.example.myapplication.dto.Project
import com.example.myapplication.dto.User
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.HomeInitPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.UserPacket
import retrofit2.Call
import retrofit2.Response

/*
 Retrofit 테스트 용 Activity
 */

class RetrofitTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = ActivityRetrofitTestBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_retrofit_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        FunClient.retrofit.getProjectDetail(1).enqueue(object : retrofit2.Callback<ProjectDetail>{
//            override fun onResponse(call: Call<ProjectDetail>, response: Response<ProjectDetail>) {
//                Log.d("test", "Test")
//
//            }
//
//            override fun onFailure(call: Call<ProjectDetail>, t: Throwable) {
//                Log.d("test", "${t.message}")
//            }
//
//        })


//        FunClient.retrofit.getProjectRankingList().enqueue(object : retrofit2.Callback<List<ProjectDetail>>{
//            override fun onResponse(call: Call<List<ProjectDetail>>, response: Response<List<ProjectDetail>>) {
//                Log.d("test", "Test")
//            }
//
//            override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
//            }
//
//        })
//
//        FunClient.retrofit.getUser("test1").enqueue(object : retrofit2.Callback<UserPacket>{
//            override fun onResponse(call: Call<UserPacket>, response: Response<UserPacket>) {
//                Log.d("test", "Test")
//            }
//
//            override fun onFailure(call: Call<UserPacket>, t: Throwable) {
//            }
//
//        })

        try {
            FunClient.retrofit.getScrollProject(1)
                .enqueue(object : retrofit2.Callback<List<ProjectDetail>> {
                    override fun onResponse(
                        call: Call<List<ProjectDetail>>,
                        response: Response<List<ProjectDetail>>
                    ) {
                        Log.d("test", "Test")
                    }

                    override fun onFailure(call: Call<List<ProjectDetail>>, t: Throwable) {
                        Log.d("test", "Test")
                    }

                })
        }
        catch(e:Exception){
            Log.d("Error Check", "${e.message}")
        }

//        FunClient.retrofit.getUser(1).enqueue(object: retrofit2.Callback<User>{
//            override fun onResponse(call: Call<User>, response: Response<User>) {
//
//
//            }
//
//            override fun onFailure(call: Call<User>, t: Throwable) {
//            }
//        })
//
//
//        FunClient.retrofit.getProjectDetail(1).enqueue(object: retrofit2.Callback<Project>{
//            override fun onResponse(call: Call<Project>, response: Response<Project>) {
//            }
//
//            override fun onFailure(call: Call<Project>, t: Throwable) {
//            }
//
//        })
//
//        val projectDetail = ProjectDetail(
//            0,
//            0,
//            "title",
//            "contents",
//            "20240906",
//            "20241013",
//            0,
//            "test1",
//            1
//        )
//
//        FunClient.retrofit.writeProject(projectDetail).enqueue(object: retrofit2.Callback<Void>{
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//            }
//
//        })
//
//
//
//        FunClient.retrofit.createFavorite(FavoritePacket(1,1)).enqueue(object: retrofit2.Callback<Void>{
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//            }
//
//        })
//
//
//        FunClient.retrofit.getCategory(1).enqueue(object:retrofit2.Callback<Category>{
//            override fun onResponse(call: Call<Category>, response: Response<Category>) {
//
//            }
//
//            override fun onFailure(call: Call<Category>, t: Throwable) {
//            }
//
//        })

    }
}