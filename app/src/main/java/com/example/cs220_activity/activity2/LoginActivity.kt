package com.example.cs220_activity.activity2

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cs220_activity.DashboardActivity
import com.example.cs220_activity.R
import com.example.cs220_activity.activity1.SignUpActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Variables
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val tvErrUsername = findViewById<TextView>(R.id.tvErrUsername)

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbPassword = findViewById<CheckBox>(R.id.cbPass)
        val tvErrPassword = findViewById<TextView>(R.id.tvErrPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val tvSignUpPage = findViewById<TextView>(R.id.tvSignUpPage)

        // Show-Hide Password
        cbPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            else etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }

        // Form Submission
        btnLogin.setOnClickListener {
            tvErrUsername.visibility = View.GONE
            tvErrPassword.visibility = View.GONE

            // Variables
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            // Empty Fields
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.err_required_fields, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Verifies Credentials
            val user = getSharedPreferences("users", MODE_PRIVATE)
            val cUsername = user.getString(username + "_username", "")
            val cPassword = user.getString(username + "_pass", "")

            if (username == cUsername) {
                if (password == cPassword) {
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                    redirectPage(DashboardActivity::class.java)
                } else {
                    tvErrPassword.visibility = View.VISIBLE
                    return@setOnClickListener
                }
            } else {
                tvErrUsername.visibility = View.VISIBLE
                return@setOnClickListener
            }
        }

        // Redirects to Sign Up Page
        tvSignUpPage.setOnClickListener {redirectPage(SignUpActivity::class.java)}
    }

    private fun redirectPage(target: Class<*>) = startActivity(Intent(this@LoginActivity, target))
}