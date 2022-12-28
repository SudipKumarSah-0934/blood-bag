package com.blood_bag.Remote

import com.blood_bag.model.*
import retrofit2.Call
import retrofit2.http.*


@JvmSuppressWildcards
interface APIService {
    //.......login........
    @POST("auth/signin")
    fun getSignInInfo(@Body signInMap: Map<String, Any>?): Call<SignInModel>
    @POST("/auth/signup")
    fun getSignUpInfo(@Body signUpMap: Map<String, Any>?): Call<SignUpModel>
    //.........Posts.........
    @GET("/post/get-divisions")
    fun getDivision(): Call<DivisionModel?>?
    @GET("/post/get-districts/{id}")
    fun getDistrict(): Call<DistrictModel?>?
    @GET("/post/get-upazilas/{id}")
    fun getUpazila(): Call<UpazilaModel?>?
   /* //.......like/unlike........
    @POST("/post/like-unlike")
    fun getLikeUnlike(@Body postLikeUnlikeMap: HashMap<String, Int>?,
                      @Header("Authorization") token: String?
    ): Call<LikeUnlikeModel>

    @DELETE("/post/delete-post/{id}")
    fun deletePost( @Header("Authorization") token: String?,
                    @Path("id") id: Int?): Call<DelPostModel?>?

    @PATCH("/post/update-post/{id}")
    fun updatePost( @Header("Authorization") token: String?,
                    @Path("id") id: Int?,
                    @Body updateMap: HashMap<String, String>?): Call<UpdatePostModel?>?

    @POST("/post/create-post")
    fun createPost( @Header("Authorization") token: String?,
                    @Body createMap: HashMap<String, String>?): Call<CreatePostModel?>?

    @GET("/post/view-comment/{id}")
    fun viewComment( @Header("Authorization") token: String?,
                     @Path("id") id: Int?): Call<ViewCommentModel?>?
    @POST("/post/create-comment/{id}")
    fun createComment( @Header("Authorization") token: String?,
                    @Path("id") id: Int?,
                    @Body createCommentMap: HashMap<String, String>?): Call<CreateCommentModel?>?
    @POST("https://api.cloudinary.com/v1_1/v2-tech/image/upload")
    fun addImage(@Part images: MultipartBody.Part?): Call<UploadImageModel?>?*/
}