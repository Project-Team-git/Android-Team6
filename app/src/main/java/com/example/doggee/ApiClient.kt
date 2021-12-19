package com.example.doggee

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient :Application(){

    public lateinit var apiService: ApiInterface
    override fun onCreate() {
        super.onCreate()
        apiService=LoginService()
    }

    fun LoginService(): ApiInterface {


            val retrofit = Retrofit.Builder()
                .baseUrl("https://android-kanini-course.cloud/doggeforlife-mobile/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return  retrofit.create(ApiInterface::class.java)



    }
}