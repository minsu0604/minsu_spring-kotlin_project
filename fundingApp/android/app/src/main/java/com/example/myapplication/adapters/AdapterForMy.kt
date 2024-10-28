package com.example.myapplication.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.activity.WriteActivity
import com.example.myapplication.databinding.ItemMyBinding

class AdapterForMy(var myList: List<String>): RecyclerView.Adapter<AdapterForMy.Holder>() {

    class Holder(val binding: ItemMyBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemMyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.textView6.text = myList[position]

        holder.itemView.setOnClickListener {
            if(myList[position] === "프로젝트 만들기") {
                val context = holder.itemView.context
                val intent = Intent(context, WriteActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}