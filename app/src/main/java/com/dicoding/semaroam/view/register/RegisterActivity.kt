package com.dicoding.semaroam.view.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.semaroam.R
import com.dicoding.semaroam.data.retrofit.ApiConfig
import com.dicoding.semaroam.data.retrofit.SignupRequest
import com.dicoding.semaroam.data.retrofit.SignupResponse
import com.dicoding.semaroam.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE)

        val registerButton: Button = findViewById(R.id.daftar_button)
        val nameEditText: EditText = findViewById(R.id.et_name)
        val usernameEditText: EditText = findViewById(R.id.et_username)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val signInLink: TextView = findViewById(R.id.signin_link)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            Log.d("RegisterActivity", "Register button clicked with name: $name, username: $username")

            if (validateRegister(name, username, password)) {
                registerUser(name, username, password)
            }
        }

        signInLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateRegister(name: String, username: String, password: String): Boolean {
        if (name.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "Nama pengguna tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Kata sandi tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Kata sandi minimal harus 6 karakter", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerUser(name: String, username: String, password: String) {
        val apiService = ApiConfig.getApiService()
        val signupRequest = SignupRequest(username, password, name)

        Log.d("RegisterActivity", "Sending signup request: $signupRequest")

        apiService.signupUser(signupRequest).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val signupResponse = response.body()
                    Log.d("RegisterActivity", "Signup response received: $signupResponse")
                    if (signupResponse != null) {
                        // Save user data to SharedPreferences
                        with(sharedPreferences.edit()) {
                            putString("user_name", signupResponse.data.nama)
                            putString("user_username", signupResponse.data.username)
                            apply()
                        }
                        Toast.makeText(this@RegisterActivity, signupResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RegisterActivity", "Signup error response: $errorBody")
                    Toast.makeText(this@RegisterActivity, "Registration failed: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.e("RegisterActivity", "Network error: ${t.localizedMessage}", t)
                Toast.makeText(this@RegisterActivity, "Network error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
