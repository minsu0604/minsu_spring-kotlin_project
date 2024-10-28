package com.example.myapplication.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MessageAdapter
import com.example.myapplication.databinding.ActivityMessageBinding
import com.google.firebase.database.*

data class Message(val text: String = "", val sender: String = "")

class MessageActivity : AppCompatActivity() {

    // Firebase Database 참조
    private lateinit var binding: ActivityMessageBinding

    private lateinit var database: DatabaseReference
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "상담원과의 채팅"

        database = FirebaseDatabase.getInstance().reference.child("messages")

        // 메시지 리스트 초기화 및 어댑터 설정
        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.messageRecyclerView.adapter = messageAdapter

        // Firebase에서 메시지 실시간 수신
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (dataSnapshot in snapshot.children) {
                    val message = dataSnapshot.getValue(Message::class.java)
                    message?.let { messageList.add(it) }
                }
                messageAdapter.notifyDataSetChanged()
                binding.messageRecyclerView.scrollToPosition(messageList.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to load messages", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.messageInput.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()  // 메시지 전송
                return@OnEditorActionListener true
            }
            false
        })

        // 메시지 전송 버튼 클릭 리스너
        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        finish()
        return true
    }

    private fun sendMessage() {
        val messageText = binding.messageInput.text.toString().trim()
        if (!TextUtils.isEmpty(messageText)) {
            val message = Message(messageText, "android")
            database.push().setValue(message)
//            binding.messageInput.text.clear()
            binding.messageInput.setText("")
        }
    }
}