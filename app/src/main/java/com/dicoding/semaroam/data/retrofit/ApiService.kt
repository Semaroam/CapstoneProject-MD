package com.dicoding.semaroam.data.retrofit

import com.dicoding.semaroam.data.response.ApiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class SignupRequest(val username: String, val name: String, val password: String)

interface ApiService {
    @POST("/signin")
    fun login(@Body request: LoginRequest): Call<ApiResponse<User>>

    @POST("/signup")
    fun register(@Body request: SignupRequest): Call<ApiResponse<User>>
}

data class User(val id: String, val name: String, val username: String)
