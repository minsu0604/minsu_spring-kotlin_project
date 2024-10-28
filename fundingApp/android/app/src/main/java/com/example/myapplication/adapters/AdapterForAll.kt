package com.example.myapplication.adapters

import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.DetailActivity
import com.example.myapplication.databinding.ItemProductAllBinding
import com.example.myapplication.dto.Project
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class AdapterForAll(var projectList: MutableList<ProjectDetail>): RecyclerView.Adapter<AdapterForAll.Holder>() {
    class Holder(val bindng: ItemProductAllBinding): RecyclerView.ViewHolder(bindng.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = Holder(ItemProductAllBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return view
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val project = projectList[position]
        holder.bindng.apply {
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
            .into(holder.bindng.imageView4)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("project", project)
            context.startActivity(intent)
        }
    }
}