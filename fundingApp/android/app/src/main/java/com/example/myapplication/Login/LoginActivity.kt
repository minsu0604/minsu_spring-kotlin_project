package com.example.myapplication.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.activity.MainActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.dto.Project
import com.example.myapplication.retrofitPacket.LoginCheckPacket
import com.example.myapplication.retrofitPacket.UserPacket
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.title = ""

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnTryLogin.setOnClickListener{
            val userId = binding.edUserId.text.toString()
            val userPw = binding.edUserPw.text.toString()

            if(userId.isEmpty()){
                displayWarningDialog("아이디를 입력해주세요.")
                return@setOnClickListener
            }

            if(userPw.isEmpty()){
                displayWarningDialog("비밀번호를 입력해주세요")
                return@setOnClickListener
            }

            val loginCheckPacket = LoginCheckPacket(userId, userPw)

            FunClient.retrofit.tryLogin(UserPacket(userId, userPw, "","","")).enqueue(object:retrofit2.Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val checkLogin = response.body() as Boolean
                    if (checkLogin) {
                        val shared =
                            getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
                        val editor = shared.edit()
                        editor.putString(Const.SHARED_PREF_LOGIN_KEY, "true")
                        editor.putString(
                            Const.SHARED_PREF_LOGIN_ID,
                            binding.edUserId.text.toString()
                        )
                        editor.commit()
                        Log.d("retrofit getProjectList", "로그인 데이터 저장 완료")
                        finish()
                    }
                    else{
                        displayWarningDialog("아이디 또는 비밀번호를 확인해주세요")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("retrofit getProjectList", "${t.message}")
                }
            })

        }

        binding.btnGoSignIn.setOnClickListener{
            startActivity(Intent(this@LoginActivity, SignInActivity::class.java))
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    fun displayWarningDialog(msg:String){
        AlertDialog.Builder(this).run{
            setMessage(msg)
            setPositiveButton("확인", null)
            show()
        }
    }
}