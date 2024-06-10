package com.dicoding.semaroam.view.profile

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.semaroam.R

class ProfileActivity : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nameTextView = findViewById(R.id.textView2)
        usernameTextView = findViewById(R.id.textView3)
        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE)

        loadUserData()
    }

    @SuppressLint("SetTextI18n")
    private fun loadUserData() {
        val name = sharedPreferences.getString("user_name", "Unknown")
        val username = sharedPreferences.getString("user_username", "Unknown")

        nameTextView.text = name
        usernameTextView.text = "@$username"
    }
}
