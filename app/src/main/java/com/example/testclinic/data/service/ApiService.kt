package com.example.testclinic.data.service

import com.example.testclinic.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @POST("users")
    fun createUser(
        @Query("name") mName: String,
        @Query("email") mEmail: String,
        @Query("gender") mGender: String,
        @Query("status") mStatus: String
    ): Call<User>

    @PUT("users/{user-id}")
    fun updateUsers(
        @Path("user-id") mUserId: Int,
        @Query("name") mName: String,
        @Query("email") mEmail: String,
        @Query("gender") mGender: String,
        @Query("status") mStatus: String
    ): Call<User>
}