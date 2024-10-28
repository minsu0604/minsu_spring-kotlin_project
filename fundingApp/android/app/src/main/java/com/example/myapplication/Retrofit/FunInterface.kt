package com.example.myapplication.Retrofit

import com.example.myapplication.dto.Category
import com.example.myapplication.retrofitPacket.FavoritePacket
import com.example.myapplication.retrofitPacket.HomeInitPacket
import com.example.myapplication.retrofitPacket.LoginCheckPacket
import com.example.myapplication.retrofitPacket.ProjectDetail
import com.example.myapplication.retrofitPacket.ProjectWrite
import com.example.myapplication.retrofitPacket.SupportPacket
import com.example.myapplication.retrofitPacket.UserFavoritePacket
import com.example.myapplication.retrofitPacket.UserPacket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface FunInterface {
    @GET("/")
    fun getHomeInitData():Call<HomeInitPacket>

    @GET("/homeScroll/{pageNum}")
    fun getScrollProject(@Path("pageNum") pageNum:Int):Call<List<ProjectDetail>>

    /* ----------------------------------------*/

    @POST("/login")
    fun tryLogin(@Body user: UserPacket) : Call<Boolean>

    @POST("/signIn")
    fun signIn(@Body user:UserPacket) : Call<Boolean>

    @GET("/user/{userId}")
    fun getUser(@Path("userId") userId: String) : Call<UserPacket>

    @POST("/logOut")
    fun logOut(@Body user: UserPacket) : Call<Void>

    /*-----------------------------------------*/

    @GET("/page/favorite/{userId}")
    fun getUserFavorite(@Path("userId") userId:String) :Call<UserFavoritePacket>


    /* ----------------------------------------*/

    // 상세 보기용
    @GET("/project/{projectId}")
    fun getProjectDetail(@Path("projectId") projectId: Int) :Call<ProjectDetail>

    @GET("/project/list")
    fun getProjectList() :Call<List<ProjectDetail>>


    // 프로젝트 인기순
    @GET("/project/list/ranking")
    fun getProjectRankingList() : Call<List<ProjectDetail>>

    @GET("/project/deadline")
    fun getProjectDeadline() : Call<List<ProjectDetail>>



    // 검색 Key로 시작하는 title을 가진 프로젝트 리스트 (10 ~ 20)?
    @GET("/project/search/{searchKey}")
    fun getSearchSuggestList(@Path("searchKey") searchKey:String) : Call<List<String>>

    @GET("project/search/result/{searchKey}")
    fun getProjectBySearchKey(@Path("searchKey") searchKey: String) : Call<List<ProjectDetail>>

    //프로젝트 작성
    @POST("/project/write")
    fun writeProject(@Body project: ProjectWrite) :Call<Void>


    @GET("/project/category/{categoryId}")
    fun getProjectByCategory(@Path("categoryId") categoryId: Int) : Call<List<ProjectDetail>>


    /*-----------------------------------------*/

    // 프로젝트에 좋아요 누른 유저 수
    @GET("/favorite/count/{projectId}")
    fun getFavoriteUserCount(@Path("projectId") projectId:Int) : Call<Int>

    // 자신이 좋아요 누른 프로젝트 리스트
    @GET("/favorite/project/{userId}")
    fun getFavoriteProject(@Path("userId") userId:String) : Call<List<ProjectDetail>>

    @POST("/favorite/check")
    fun checkFavorite(@Body favoritePacket: FavoritePacket) :Call<Boolean>

    @POST("/favorite")
    fun createFavorite(@Body favoritePacket: FavoritePacket) :Call<Void>

    //FavoriteDelPacket => projectId + userId
    @HTTP(method ="DELETE", path="/favorite/delete", hasBody = true)
    fun deleteFavorite(@Body favoritePacket: FavoritePacket) : Call<Void>

    /*-----------------------------------------*/

    // 후원한 유저 수
    @GET("/support/count/{projectId}")
    fun getSupportUserCount(@Path("projectId") projectId: Int) : Call<Int>

    // 자신이 후원한 프로젝트 리스트
    @GET("/support/project/{userId}")
    fun getSupportingProject(@Path("userId") userId:Int) : Call<List<ProjectDetail>>

    @POST("/support/check")
    fun checkSupporting(@Body support:SupportPacket) : Call<Boolean>

    @POST("/support")
    fun createSupport(@Body supportPacket: SupportPacket) : Call<Void>

    //SupportDelPacket => projectId + userId
    @HTTP(method = "DELETE", path = "/support/delete", hasBody = true)
    fun getSupportDelete(@Body supportPacket: SupportPacket) : Call<Void>

    /*-----------------------------------------*/

    @GET("/category/{categoryId}")
    fun getCategory(@Path("categoryId") categoryId:Int) : Call<Category>
}