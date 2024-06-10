package com.dicoding.semaroam.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.semaroam.R
import com.dicoding.semaroam.data.retrofit.ApiConfig
import com.dicoding.semaroam.data.retrofit.LoginRequest
import com.dicoding.semaroam.data.retrofit.LoginResponse
import com.dicoding.semaroam.view.register.RegisterActivity
import com.dicoding.semaroam.view.start.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.login_button)
        val usernameEditText: EditText = findViewById(R.id.et_username)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val signUpLink: TextView = findViewById(R.id.register_link)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            Log.d("LoginActivity", "Login button clicked with username: $username")

            if (validateLogin(username, password)) {
                loginUser(username, password)
            }
        }

        signUpLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLogin(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            Toast.makeText(this, "Nama pengguna tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Kata sandi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun loginUser(username: String, password: String) {
        val apiService = ApiConfig.getApiService()
        val loginRequest = LoginRequest(username, password)

        Log.d("LoginActivity", "Sending login request: $loginRequest")

        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LoginActivity", "Login response received: $loginResponse")
                    if (loginResponse?.accessToken != null) {
                        Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed: ${loginResponse?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("LoginActivity", "Login error response: $errorBody")
                    Toast.makeText(this@LoginActivity, "Login error: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Network error: ${t.localizedMessage}", t)
                Toast.makeText(this@LoginActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
