package com.example.myapplication.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.CategoryActivity
import com.example.myapplication.databinding.ItemCategoryBinding
import com.example.myapplication.dto.Category
import com.example.myapplication.retrofitPacket.CategoryPacket

class AdapterForCategory(var categoryList: List<CategoryPacket>): RecyclerView.Adapter<AdapterForCategory.Holder>() {
    class Holder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val category = categoryList[position]

        holder.binding.imageView2.setImageResource(imageResource(category.categoryId))
        holder.binding.textViewTitle.text = category.title

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("category", category)
            context.startActivity(intent)
        }
    }

    fun imageResource(num: Int): Int {
        return when (num) {
            0 -> R.drawable.cate1
            1 -> R.drawable.cate2
            2 -> R.drawable.cate3
            3 -> R.drawable.cate4
            4 -> R.drawable.cate5
            5 -> R.drawable.cate6
            6 -> R.drawable.cate7
            7 -> R.drawable.cate8
            8 -> R.drawable.cate9
            9 -> R.drawable.cate10
            10 -> R.drawable.cate11
            else -> R.drawable.cate1 // 기본 이미지 또는 오류 처리
        }
    }
}