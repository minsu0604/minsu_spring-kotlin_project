package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemSearchBinding

class AdapterForSearch(): RecyclerView.Adapter<AdapterForSearch.Holder>() {

    val searchWords = listOf(
        "타로", "향수", "한복",
        "가방", "보드게임", "머더미스터리",
        "반지", "인형", "추석",
        "옷")

    class Holder(val binding: ItemSearchBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return searchWords.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textViewNum.text = "${position + 1}"
        holder.binding.textViewTitle.text = searchWords[position]
    }
}