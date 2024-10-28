package com.example.myapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemBannerBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class AdapterForBanner(var imageList: List<String>) :
    RecyclerView.Adapter<AdapterForBanner.Holder>() {
    class Holder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        if (imageList.size != 0) {
            return imageList.size + 2
        } else {
            return 0
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val realPosition = when (position) {
            0 -> imageList.size - 1
            imageList.size + 1 -> 0
            else -> position - 1
        }

        val image = imageList[realPosition]

        Picasso.get()
            .load(image)
            .error(R.drawable.thumb)
            .into(holder.binding.imageView)
    }


}