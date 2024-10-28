package com.example.myapplication.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemProductBinding
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class AdapterForProduct(var projectList: List<ProjectDetail>): RecyclerView.Adapter<AdapterForProduct.Holder>() {
    class Holder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
            textViewDeadline.text = project.calculateDday()
        }
        Picasso.get()
            .load(project.thumbnail)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .error(R.drawable.thumb)
            .into(holder.binding.imageView4)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("project", project)
            context.startActivity(intent)
        }

    }
}