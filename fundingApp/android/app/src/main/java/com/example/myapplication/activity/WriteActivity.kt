package com.example.myapplication.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.databinding.ActivityWriteBinding
import com.example.myapplication.retrofitPacket.CategoryPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.ProjectWrite
import com.example.myapplication.retrofitPacket.UserPacket
import com.example.myapplication.utils.Const
import retrofit2.Call
import retrofit2.Response

class WriteActivity : AppCompatActivity() {
    lateinit var binding:ActivityWriteBinding
    lateinit var user: UserPacket

    val categoryMap = mapOf(
        0 to CategoryPacket(0, "전체"),
        1 to CategoryPacket(1, "캐릭터 · 굿즈"),
        2 to CategoryPacket(2, "홈 · 리빙"),
        3 to CategoryPacket(3, "테크 · 가전"),
        4 to CategoryPacket(4, "반려동물"),
        5 to CategoryPacket(5, "향수 · 뷰티"),
        6 to CategoryPacket(6, "의류"),
        7 to CategoryPacket(7, "잡화"),
        8 to CategoryPacket(8, "음악"),
        9 to CategoryPacket(9, "푸드"),
        10 to CategoryPacket(10, "주얼리")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_write)

        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "프로젝트 만들기"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var selectedCategory: CategoryPacket? = null // 카테고리
        lateinit var title: String // 타이틀
        var imagePath: String? = null // 이미지 절대 경로
        lateinit var contentText: String // 내용
        lateinit var goalAmount: String // 목표 금액
        lateinit var perPrice: String // 후원 금액(개당 금액)
        lateinit var startDate: String // 펀딩 일정(시작일)
        lateinit var endDate: String // 펀딩 일정(종료일)

        // 카테고리 선택
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목의 ID에 따라 CategoryPacket 생성
                selectedCategory = categoryMap[position]
                Log.d("category", "selectedCategory: $selectedCategory")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 권한 확인
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        // 현재 선택된 ImageView를 저장할 변수
        var selectedImageView: ImageView? = null

        // 이미지 선택
        val requestGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data

                imageUri?.let { uri ->
                    // 절대 경로 가져옴
                    //imagePath = getRealPathFromURI(uri).toString()

                    // 선택된 ImageView에 이미지 설정
                    selectedImageView?.setImageURI(uri)

                } ?: run {
                    Log.d("ImageLoad", "Image URI is null")
                }
            }
        }

        fun pickImage() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // imageView1 클릭
        binding.imageView1.setOnClickListener {
            selectedImageView = binding.imageView1
            pickImage()
        }

        // imageView2 클릭
        binding.imageView2.setOnClickListener {
            selectedImageView = binding.imageView2
            pickImage()
        }

        // imageView3 클릭
        binding.imageView3.setOnClickListener {
            selectedImageView = binding.imageView3
            pickImage()
        }

        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        // 펀딩 일정(시작일) 버튼 클릭
        binding.tvStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                // "YYYY-MM-DD" 형식으로 날짜 설정
                binding.tvStartDate.text = String.format("%04d-%02d-%02d", year, month + 1, day)
            }, year, month, day)
            datePickerDialog.show()
        }

        // 펀딩 일정(종료일) 버튼 클릭
        binding.tvEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                // "YYYY-MM-DD" 형식으로 날짜 설정
                binding.tvEndDate.text = String.format("%04d-%02d-%02d", year, month + 1, day)
            }, year, month, day)
            datePickerDialog.show()
        }

        // 작성 완료 버튼 클릭
        binding.btnSubmit.setOnClickListener {
            title = binding.edtTitle.text.toString()
//            contentText = binding.edtContents.getText().toString()
            contentText = "없음"
            startDate = binding.tvStartDate.getText().toString() + "T00:00:00"
            endDate = binding.tvEndDate.getText().toString() + "T00:00:00"
            goalAmount = binding.edtGoalAmount.text.toString()
            perPrice = binding.edtPerPrice.text.toString()

        //Toast.makeText(this@WriteActivity, "작성 완료", Toast.LENGTH_SHORT).show()
//            // 프로젝트 디테일 화면으로 이동
//            val intent = Intent(this@WriteActivity, DetailActivity::class.java)
//            FunClient.retrofit.getProjectDetail(103).enqueue(object : retrofit2.Callback<ProjectDetail> {
//                override fun onResponse(call: Call<ProjectDetail>, response: Response<ProjectDetail>) {
//                    val detailProject = response.body()
//                    Log.d("WriteActivity", "${detailProject}")
//                    intent.putExtra("project", detailProject)
//                    intent.putExtra("toHome", true)
//                    startActivity(intent)
//                }
//
//                override fun onFailure(call: Call<ProjectDetail>, t: Throwable) {
//                }
//            })

//            if(title == "" || imagePath == null || contentText == "" || startDate == "T00:00:00" || endDate == "T00:00:00" || goalAmount == "" || perPrice == "") {
//                Toast.makeText(this@WriteActivity, "내용을 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
//            }
//            else {
                val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
                val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")

                // userId가 유효한지 확인
                if (userId != "false") {
                    // user 정보 가져옴
                    FunClient.retrofit.getUser(userId!!).enqueue(object : retrofit2.Callback<UserPacket> {
                        override fun onResponse(call: Call<UserPacket>, response: Response<UserPacket>) {
                            if (response.isSuccessful) {
                                val user = response.body()
                                Log.d("WriteActivity", "${response.body()}")
                                // user 정보가 정상적으로 반환된 경우
                                if (user != null) {
                                    val projectWrite = ProjectWrite(
                                        103, goalAmount.toInt(), 0, title, "103.png", startDate, endDate, perPrice.toInt(), "103.jpg", user, selectedCategory!!
                                    )

                                    FunClient.retrofit.writeProject(projectWrite).enqueue(object : retrofit2.Callback<Void> {
                                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                            if (response.isSuccessful) {
                                                Toast.makeText(this@WriteActivity, "작성 완료", Toast.LENGTH_SHORT).show()

                                                // 프로젝트 디테일 화면으로 이동
                                                val intent = Intent(this@WriteActivity, DetailActivity::class.java)
                                                FunClient.retrofit.getProjectDetail(103).enqueue(object : retrofit2.Callback<ProjectDetail> {
                                                    override fun onResponse(call: Call<ProjectDetail>, response: Response<ProjectDetail>) {
                                                        val detailProject = response.body()
                                                        Log.d("WriteActivity", "${detailProject}")
                                                        intent.putExtra("project", detailProject)
                                                        intent.putExtra("toHome", true)
                                                        startActivity(intent)
                                                    }

                                                    override fun onFailure(call: Call<ProjectDetail>, t: Throwable) {
                                                    }
                                                })
                                            }
                                        }

                                        override fun onFailure(call: Call<Void>, t: Throwable) {
                                        }
                                    })
                                }
                            }
                        }

                        override fun onFailure(call: Call<UserPacket>, t: Throwable) {
                            Log.e("Submit", "Error: ${t.message}")
                        }
                    })
                }
//            }
        }
    }

    // 이미지 절대 경로
//    fun getRealPathFromURI(uri: Uri): String? {
//        val projection = arrayOf(MediaStore.Images.Media.DATA)
//        contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
//            if (cursor.moveToFirst()) {
//                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                return cursor.getString(columnIndex)
//            }
//        }
//        return null
//    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        finish()
        return true
    }

}