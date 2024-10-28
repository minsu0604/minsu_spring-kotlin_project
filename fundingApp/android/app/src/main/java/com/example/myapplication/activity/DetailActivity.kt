package com.example.myapplication.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Login.LoginActivity
import com.example.myapplication.R
import com.example.myapplication.Retrofit.FunClient
import com.example.myapplication.adapters.AdapterForBanner
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.SupportPacket
import com.example.myapplication.utils.Const
import com.example.myapplication.utils.Const.SERVER_BASE_URL
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    lateinit var binding:ActivityDetailBinding
    lateinit var project:ProjectDetail

    var isFavorite = false
    var isLoggedIn = false
    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.myapplication.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "상세 페이지"

        project = intent.getSerializableExtra("project") as ProjectDetail

        var detailImages = listOf(project.thumbnail)

        binding.viewPager2.adapter = AdapterForBanner(detailImages)
        binding.textViewCategory.text = project.category.title
        binding.textViewTitle.text = project.title
        binding.textViewSupport.text = project.formattedAmount()
        binding.textViewPercent.text = project.percent()
        binding.progressBar.progress = project.progress()

        Picasso.get()
            .load(project.contents)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .error(R.drawable.detail_ex2)
            .into(binding.imageView5)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.setCustomView(R.layout.custom_tabl)
        }.attach()


        // 좋아요 클릭
        binding.buttonFavorite.setOnClickListener {
            if(isLoggedIn == false){
                displayRequiredLoginDialog()
                return@setOnClickListener
            }

            // user 정보 가져오기
            val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
            val userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "false")
            Log.d("DetailActivity", "${userId}")

            val FavoritePacket = FavoritePacket(project.projectId, userId!!)

            // 좋아요 여부에 따라 추가 및 제거
            if(isFavorite){
                // favorite db에서 제거
                FunClient.retrofit.deleteFavorite(FavoritePacket).enqueue(object :retrofit2.Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        isFavorite = false
                        fillFavorite(false)
                        Toast.makeText(this@DetailActivity, "좋아요를 취소했습니다", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                    }

                })
            }else {
                // favorite db에 저장
                FunClient.retrofit.createFavorite(FavoritePacket)
                    .enqueue(object : retrofit2.Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful) {
                                Log.d("DetailActivity", "${response.body()}")
                                fillFavorite(true)

                                Toast.makeText(
                                    this@DetailActivity,
                                    "좋아요를 눌렀습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                isFavorite = true
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.d("DetailActivity", "좋아요 실패")
                        }
                    })
            }
        }

        // 공유 버튼
        binding.buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"  // 공유할 데이터 타입 (텍스트, 이미지, 비디오 등)
                val projectUrl = "${Const.SERVER_BASE_URL + "/project/" + project.projectId}"
                putExtra(Intent.EXTRA_TEXT, "${projectUrl}")
            }
            val chooser = Intent.createChooser(shareIntent, "공유할 앱을 선택 해 주세요")  // 사용자에게 앱 선택 옵션을 제공
            startActivity(chooser)
        }


        // 후원 버튼
        binding.buttonSupport.isEnabled = false
        binding.buttonSupport.setOnClickListener{
            if(isLoggedIn == false){
                displayRequiredLoginDialog()
                return@setOnClickListener
            }

            val activityContext = this

            val dialogView = layoutInflater.inflate(R.layout.dialog_card_info, null)
            val cardEd1 = dialogView.findViewById<EditText>(R.id.ed_card_1)
            val cardEd2 = dialogView.findViewById<EditText>(R.id.ed_card_2)
            val cardEd3 = dialogView.findViewById<EditText>(R.id.ed_card_3)
            val cardEd4 = dialogView.findViewById<EditText>(R.id.ed_card_4)

            val cardDate = dialogView.findViewById<EditText>(R.id.ed_card_end_date)
            val cardCvc = dialogView.findViewById<EditText>(R.id.ed_cvc)

            val cardOk = dialogView.findViewById<Button>(R.id.buttonPay)

            // 카드 입력 Dialog
            val cardInfoDialog = AlertDialog.Builder(this).run{
                setTitle("후원하기")
                setMessage("카드정보를 입력해주세요")
                setView(dialogView)
                show()
            }

            cardOk.setOnClickListener{
                if(cardEd1.text.toString().isEmpty() ||
                    cardEd2.text.toString().isEmpty() ||
                    cardEd3.text.toString().isEmpty() ||
                    cardEd4.text.toString().isEmpty() ||
                    cardDate.text.toString().isEmpty()||
                    cardCvc.text.toString().isEmpty()){
                    Toast.makeText(activityContext, "카드 정보를 전부 입력해주세요", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // 후원
                FunClient.retrofit.createSupport(SupportPacket(project.projectId, userId)).enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("Support", "test")
                        cardInfoDialog.cancel()

                        project.currentAmount += project.perPrice

                        binding.textViewSupport.text = project.formattedAmount()
                        binding.textViewPercent.text = project.percent()
                        binding.progressBar.progress = project.progress()

                        binding.buttonSupport.text = "이미 후원한 프로젝트입니다"
                        binding.buttonSupport.isEnabled = false

                        Toast.makeText(activityContext, "후원이 완료되었습니다", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        cardInfoDialog.cancel()
                    }
                })
            }

        }

    }

    private fun resumeMethod(){
        val shared = getSharedPreferences(Const.SHARED_PREF_LOGIN_NAME, Context.MODE_PRIVATE)
        isLoggedIn = shared?.getString(Const.SHARED_PREF_LOGIN_KEY, "false") == "true"
        if(isLoggedIn == true){
            userId = shared?.getString(Const.SHARED_PREF_LOGIN_ID, "").toString()
        }

    }

    override fun onResume() {
        super.onResume()

        resumeMethod()


        if(isLoggedIn) {
            // 후원 여부 확인
            FunClient.retrofit.checkSupporting(SupportPacket(project.projectId, userId))
                .enqueue(object : retrofit2.Callback<Boolean> {
                    override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                        val isSupporting = response.body() as Boolean
                        if (isSupporting) {
                            binding.buttonSupport.text = "이미 후원한 프로젝트입니다"
                        } else {
                            binding.buttonSupport.isEnabled = true
                        }
                    }

                    override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    }

                })

            // 좋아요 여부 확인
            FunClient.retrofit.checkFavorite(FavoritePacket(project.projectId, userId)).enqueue(object : retrofit2.Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    isFavorite = response.body() as Boolean
                    if(isFavorite){
                        fillFavorite(true)
                    } else {
                        fillFavorite(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if(intent.getBooleanExtra("toHome", false)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            finish()
        }

        return true
    }

    fun fillFavorite(fill: Boolean) {
        if (fill) {
            binding.buttonFavorite.setIconResource(R.drawable.heart_icon)
            binding.buttonFavorite.setIconTintResource(R.color.orange)
        } else {
            binding.buttonFavorite.setIconResource(R.drawable.tab_favorite)
            binding.buttonFavorite.setIconTintResource(R.color.gray)
        }
    }


    fun displayRequiredLoginDialog(){
        AlertDialog.Builder(this).run{
            setTitle("로그인이 필요합니다.")
            setMessage("로그인 하시겠습니까?")
            setNegativeButton("취소", null)
            setPositiveButton("확인", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    startActivity(Intent(this@DetailActivity, LoginActivity::class.java))
                }
            })
            show()
        }
    }
}