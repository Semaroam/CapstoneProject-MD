package com.dicoding.semaroam.view.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.semaroam.R
import com.dicoding.semaroam.view.register.RegisterActivity
import com.dicoding.semaroam.view.start.HomeActivity

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton: Button = findViewById(R.id.login_button)
        val usernameEditText: EditText = findViewById(R.id.et_username)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val signUpLink: TextView = findViewById(R.id.register_link)

        loginButton.setOnClickListener {
            // Mulai langsung ke HomeActivity sementara autentikasi dinonaktifkan
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish() // Tambahkan ini untuk menutup LoginActivity setelah memulai HomeActivity
        }


        signUpLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
