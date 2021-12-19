package com.example.doggee

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("login")
    fun login(@Body request:LoginRequest): Call<User>

    @Headers("Content-Type:application/json")
    @POST("register")
    fun RegUser(@Body users:LoginRequest):Call<Void>

    @Headers("Content-Type:application/json")
    @POST("users/me/email")
    fun changeUserEMail(@Body mail:EmailChangeRequest,@Header("Authorization") token: String):Call<Void>

    @DELETE("users/me")
    fun deleteUser(@Header("Authorization") token: String):Call<Void>

    @GET("pets")
    suspend fun GetPets(@Header("Authorization") token: String): Response<AllPets>

    @GET("users/me/loginHistory")
    suspend fun logDetails(@Header("Authorization") token: String): Response<LogList>

    @GET("users")
    suspend fun otherUserDetails(@Header("Authorization") token: String): Response<otherUserList>

    @Headers("Content-Type:application/json")
    @POST("users/me/petInterests")
    fun addInterest(@Body dog: DogId, @Header("Authorization") token: String):Call<Void>


    @GET("users/me/petInterests")
    suspend fun getMyInterest(@Header("Authorization") token: String): Response<IntList>

    @DELETE("users/me/petInterests/{petinterestId}")
     fun deleteInterest(@Path("petinterestId") id: Int,@Header("Authorization") token: String):Call<Void>
}