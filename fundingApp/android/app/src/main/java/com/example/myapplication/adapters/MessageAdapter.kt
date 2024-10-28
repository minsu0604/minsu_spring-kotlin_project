package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.Message

class MessageAdapter(private val messageList: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    // ViewHolder 클래스 정의
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]
        holder.messageText.text = message.text
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return if (message.sender == "android") {
            R.layout.item_message_sent // 내가 보낸 메시지 레이아웃
        } else {
            R.layout.item_message_received // 상대방이 보낸 메시지 레이아웃
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}
