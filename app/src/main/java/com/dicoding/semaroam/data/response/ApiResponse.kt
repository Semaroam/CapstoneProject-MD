package com.dicoding.semaroam.data.response

data class ApiResponse<T>(
    val success: Boolean, // Indicates if the API call was successful
    val message: String, // Describes the result or error
    val data: T?, // Contains
)