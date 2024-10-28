package com.example.myapplication.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.adapters.AdapterForMy
import com.example.myapplication.databinding.FragmentMyBinding
import com.example.myapplication.retrofitPacket.UserPacket
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

class MyFragment : Fragment() {


    lateinit var binding:FragmentMyBinding
    lateinit var adapterForMy: AdapterForMy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyBinding.inflate(layoutInflater)

        val myList = listOf<String>(
            "내 후기",
            "공지사항",
            "헬프센터",
            "문의하기",
            "내가 만든 프로젝트",
            "창작자 가이드",
            "프로젝트 만들기"
        )

        adapterForMy = AdapterForMy(myList)
        binding.recyclerView2.adapter = adapterForMy
        binding.recyclerView2.layoutManager = LinearLayoutManager(this.context)



        return binding.root
    }


    override fun onResume() {
        super.onResume()
        val shared = activity?.getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, null) == "true"
        val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")

        if (isLoggedIn) {
            // 로그인 상태

            binding.imageViewProfile.visibility = View.VISIBLE
            binding.textViewId.visibility = View.VISIBLE
            binding.textViewFollow.visibility = View.VISIBLE

            binding.buttonLogout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonLogout.setText("로그아웃")

            adapterForMy.myList = listOf(
                "내 후기",
                "공지사항",
                "헬프센터",
                "문의하기",
                "내가 만든 프로젝트",
                "창작자 가이드",
                "프로젝트 만들기"
            )

            adapterForMy.notifyDataSetChanged()

            binding.buttonLogout.setOnClickListener {
                // 로그아웃 로직
                val editor = shared?.edit()
                editor?.putString(Const.SHARED_PREF_LOGIN_KEY, "false")
                editor?.putString(Const.SHARED_PREF_LOGIN_ID, "")
                editor?.commit()

                val intent = Intent(this@MyFragment.context, MainActivity::class.java)
                startActivity(intent)

            }

            // user 정보 가져오기
            FunClient.retrofit.getUser(userId!!).enqueue(object: retrofit2.Callback<UserPacket>{
                override fun onResponse(call: Call<UserPacket>, response: Response<UserPacket>) {
                    binding.textViewId.setText(response.body()?.name ?: "알 수 없음")
                }

                override fun onFailure(call: Call<UserPacket>, t: Throwable) {

                }
            })

            binding.imageViewProfile.setImageResource(R.drawable.profile)
        } else {
            // 로그아웃 상태

            binding.imageViewProfile.visibility = View.GONE
            binding.textViewId.visibility = View.GONE
            binding.textViewFollow.visibility = View.GONE

            binding.buttonLogout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange))
            binding.buttonLogout.setText("로그인")

            adapterForMy.myList = listOf(
                "공지사항",
                "헬프센터",
                "문의하기",
                "창작자 가이드",
            )

            adapterForMy.notifyDataSetChanged()

            binding.buttonLogout.setOnClickListener {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}