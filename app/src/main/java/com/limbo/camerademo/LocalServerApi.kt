package com.limbo.camerademo

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

// http://192.168.15.82:5000/test
interface LocalServerApi {
//    @Multipart
//    @POST("/uploadPhoto")
//    Call<PhotoModel> sendPhoto(@Part("photo") photo: RequestBody)

    @Multipart
    @POST("/uploadPhoto")
    fun sendPhoto(@Part photoFile: MultipartBody.Part, @Part("photo") photo: RequestBody): Call<PhotoModel>

    @GET("/test")
    fun testRoute(): Call<TestModel>
}