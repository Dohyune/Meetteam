package com.example.meetteam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetteam.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernameEditText: EditText = binding.username
        val passwordEditText: EditText = binding.password
        val loginButton: Button = binding.loginButton

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username == "user" && password == "password") {
                val sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
