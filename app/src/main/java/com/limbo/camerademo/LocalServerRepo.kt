package com.limbo.camerademo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

const val BASE_URL = "http://192.168.15.82:5000"
class LocalServerRepo {
    private val TAG = "LocalServerRepo"
    private val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder().build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val localServerService by lazy {
        retrofit.create(LocalServerApi::class.java)
    }

    fun uploadPhoto(file: File): LiveData<PhotoModel> {
        Log.d(TAG, "uploadPhoto Called")
        val data = MutableLiveData<PhotoModel>()

        val imageReqBody: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val part: MultipartBody.Part = MultipartBody.Part.createFormData("out", file.name, imageReqBody)
        val description: RequestBody = RequestBody.create(MediaType.parse("text/plain"), "image-type")


        Log.d(TAG, "Content-Type: ${imageReqBody.contentType()}")
        Log.d(TAG, "Content-Length: ${imageReqBody.contentLength()}")
        Log.d(TAG, "${imageReqBody.toString()}")

        Log.d(TAG, "file name ${file.name}")

        Log.d(TAG, "part headers: ${part.headers()?.toString()}")


        localServerService.sendPhoto(part, description).enqueue(object: Callback<PhotoModel> {
            override fun onFailure(call: Call<PhotoModel>, t: Throwable) {
                Log.d(TAG, ".sendPhoto onFailure called")
                Log.d(TAG, ".sendPhoto onFailure cause: ${t.message}")
            }

            override fun onResponse(call: Call<PhotoModel>, response: Response<PhotoModel>) {
                Log.d(TAG, ".sendPhoto onResponse called")
                if(response.isSuccessful) {
                    data.value = response.body()
                } else {
                    Log.d(TAG, ".sendPhoto response not successful")
                    Log.d(TAG, ".sendPhoto ${response.errorBody()?.string()}")
                }
            }
        })

        return data
    }

    fun testRoute(): LiveData<TestModel> {
        val data = MutableLiveData<TestModel>()
        localServerService.testRoute().enqueue(object: Callback<TestModel> {
            override fun onFailure(call: Call<TestModel>, t: Throwable) {
                Log.d(TAG, "TEST ROUTE FAILED")
                Log.d(TAG, "TEST ROUTE: FAILED: ${t.message}")
            }

            override fun onResponse(call: Call<TestModel>, response: Response<TestModel>) {
                Log.d(TAG, "TEST ROUTE ON RESPONSE CALLED")
                if(response.isSuccessful) {
                    Log.d(TAG, "TEST ROUTE ON RESPONSE CALLED GOOD")
                    Log.d(TAG, "${response.body().toString()}")
                    data.value = response.body()
                } else {
                    Log.d(TAG, "TEST ROUTE RESPONSE FAILLEED")
                }
            }
        })

        return data
    }
}