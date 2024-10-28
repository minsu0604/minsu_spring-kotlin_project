package com.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemCategoryDetailBinding
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.utils.Const
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class AdapterForCategoryDetail(var projectList: MutableList<ProjectDetail>): RecyclerView.Adapter<AdapterForCategoryDetail.Holder>() {
    class Holder(val binding: ItemCategoryDetailBinding): RecyclerView.ViewHolder(binding.root)

    var userId:String? = null
    var favoriteList:MutableList<Int>? = null
    private var isFavorite:Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val project = projectList[position]
        holder.binding.apply {
            textViewUser.text = project.user.name
            textViewTitle.text = project.title
            textViewTotal.text = project.percent()
            progressBar.progress = project.progress()
            textViewDeadline.text = project.calculateDday()
        }

        Picasso.get()
            .load(project.thumbnail)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .error(R.drawable.thumb)
            .into(holder.binding.imageView5)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("project", project)
            context.startActivity(intent)
        }


        if(favoriteList != null && favoriteList!!.contains(project.projectId)){
            holder.binding.imageButton.setImageResource(R.drawable.heart_icon)
            isFavorite = true
        }
        else{
            holder.binding.imageButton.setImageResource(R.drawable.tab_favorite)
            isFavorite = false
        }

        val imgBtn = holder.binding.imageButton
        imgBtn.setOnClickListener{
            if(userId == null){
                Toast.makeText(holder.itemView.context, "로그인 후 이용 가능합니다", Toast.LENGTH_SHORT).show()
            }
            else{
                // 좋아요 여부에 따라 추가 및 제거
                if(isFavorite){
                    // favorite db에서 제거
                    FunClient.retrofit.deleteFavorite(FavoritePacket(projectList[position].projectId, userId!!)).enqueue(object :retrofit2.Callback<Void>{
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            isFavorite = false
                            imgBtn.setImageResource(R.drawable.tab_favorite)
                            Toast.makeText(holder.itemView.context, "좋아요를 취소했습니다", Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                        }

                    })
                }else {
                    // favorite db에 저장
                    FunClient.retrofit.createFavorite(FavoritePacket(projectList[position].projectId, userId!!))
                        .enqueue(object : retrofit2.Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    Log.d("DetailActivity", "${response.body()}")
                                    imgBtn.setImageResource(R.drawable.heart_icon)
                                    Toast.makeText(
                                        holder.itemView.context,
                                        "좋아요를 눌렀습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isFavorite = true
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("DetailActivity", "좋아요 실패")
                            }
                        })
                }
            }
        }

    }


}