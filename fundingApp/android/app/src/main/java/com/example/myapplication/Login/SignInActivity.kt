package com.example.myapplication.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivitySignInBinding
import com.example.myapplication.dto.Favorite
import com.example.myapplication.dto.Project
import com.example.myapplication.dto.Support
import com.example.myapplication.dto.User
import com.example.myapplication.retrofitPacket.UserPacket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "회원가입"

        val binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                Log.d("search address", "${it.data!!.getStringExtra("data")}")

                var addressInfo = it.data!!.getStringExtra("data")!!.split("/")
                val zoneCode = addressInfo[1] // 우편번호
                val roadAddress = addressInfo[0] // 도로주소

                binding.tvPostNumber.setText(zoneCode)
                binding.tvUserAddress.setText(roadAddress)
            }
        }

        binding.btnSearchAddress.setOnClickListener{
            val intent = Intent(this@SignInActivity, SearchAddressActivity::class.java)
            launcher.launch(intent)
        }

        binding.btnSignIn.setOnClickListener{
            val userId = binding.edUserId.text.toString()
            val userPw = binding.edUserPw.text.toString()
            val userEmail = binding.edUserEmail.text.toString()
            val userName = binding.edUserName.text.toString()
            val address = binding.tvUserAddress.text.toString()
            val detailAddr = binding.edUserDetailAddress.text.toString()


            if(userId.isEmpty()){
                displayWarningDialog("아이디를 입력해주세요")
                return@setOnClickListener
            }

            if(userPw.isEmpty()){
                displayWarningDialog("비밀번호를 입력해주세요")
                return@setOnClickListener
            }

            if(userEmail.isEmpty()){
                displayWarningDialog("이메일을 입력해주세요")
                return@setOnClickListener
            }

            if(userName.isEmpty()){
                displayWarningDialog("이름을 입력해주세요")
                return@setOnClickListener
            }

            if(address.isEmpty()){
                displayWarningDialog("주소를 입력해주세요")
                return@setOnClickListener
            }

            if(detailAddr.isEmpty()){
                displayWarningDialog("상세 주소를 입력해주세요")
                return@setOnClickListener
            }


            val fullAddress = "$address $detailAddr" // 전체 주소 = 도로명 주소 + 상세 주소

            val user = UserPacket(
                userId,
                userPw,
                userName,
                userEmail,
                fullAddress
            )


            FunClient.retrofit.signIn(user).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    val result = response.body() as Boolean

                    if(result == true){
                        Log.d("retrofit try sign in", "successful")
                        finish()
                        return
                    }
                    else{
                        displayWarningDialog("이미 존재하는 아이디 입니다")
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("retrofit try sign in", "onFailure : ${t.message}")
                }
            })
        }

//        binding.btnCancel.setOnClickListener{
//            startActivity(Intent(this@SignInActivity, LoginActivity::class.java))
//        }
    }


    fun displayWarningDialog(msg:String){
        AlertDialog.Builder(this).run{
            setMessage(msg)
            setPositiveButton("확인", null)
            show()
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        finish()
        return true
    }

}