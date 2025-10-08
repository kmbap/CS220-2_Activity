package com.example.cs220_activity.activity1

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cs220_activity.activity2.LoginActivity
import com.example.cs220_activity.R
import java.text.SimpleDateFormat
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Variables
        val etFullName = findViewById<EditText>(R.id.etFullName)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val tvErrEmail = findViewById<TextView>(R.id.tvErrEmail)

        val rgGender = findViewById<RadioGroup>(R.id.rgGender)
        val etBirthdate = findViewById<EditText>(R.id.etBirthdate)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val tvErrUsername = findViewById<TextView>(R.id.tvErrUsername)

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbPassword = findViewById<CheckBox>(R.id.cbPass)
        val tvErrPass = findViewById<TextView>(R.id.tvErrPass)

        val etCPass = findViewById<EditText>(R.id.etCPass)
        val tvErrPassMatch = findViewById<TextView>(R.id.tvErrPassMatch)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        val tvLoginPage = findViewById<TextView>(R.id.tvLoginPage)

        // Validates Email
        etEmail.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            val email = etEmail.text.toString()

            if (!hasFocus) {
                if (isEmailValid(email)) {
                    tvErrEmail.visibility = View.GONE
                } else tvErrEmail.visibility = View.VISIBLE
            } else tvErrEmail.visibility = View.GONE
        }

        // Verifies if username already exists
        etUsername.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            val username = etUsername.text.toString()

            val user = getSharedPreferences("users", MODE_PRIVATE)
            val cUsername = user.getString(username + "_username", "")

            if (!hasFocus) {
                if (username != cUsername) {
                    tvErrUsername.visibility = View.GONE
                } else tvErrUsername.visibility = View.VISIBLE
            } else tvErrUsername.visibility = View.GONE
        }

        // Display Calendar
        etBirthdate.setOnClickListener {showDatePickerDialog(etBirthdate)}

        // Validates Password
        etPassword.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val pass = etPassword.text.toString().trim()

                if (!isPassValid(pass)) tvErrPass.visibility = View.VISIBLE
                else tvErrPass.visibility = View.GONE
            } else tvErrPass.visibility = View.GONE
        }

        // Show-Hide Password
        cbPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                etCPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                etCPass.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        // Verifies Password Match
        etCPass.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                val pass = etPassword.text.toString().trim()
                val cpass = etCPass.text.toString().trim()

                tvErrPassMatch.visibility = View.VISIBLE
                when {
                    cpass.isEmpty() -> tvErrPassMatch.setText(R.string.err_pass_empty)
                    cpass != pass -> tvErrPassMatch.setText(R.string.err_pass_match)
                    else -> tvErrPassMatch.visibility = View.GONE
                }
            } else tvErrPassMatch.visibility = View.GONE
        }

        // Form Submission
        btnSignUp.setOnClickListener {
            // Variables
            var hasError = false

            val name = etFullName.text.toString()
            val email = etEmail.text.toString()
            val bdate = etBirthdate.text.toString()
            val username = etUsername.text.toString()
            val pass = etPassword.text.toString()
            val cpass = etCPass.text.toString()

            val user = getSharedPreferences("users", MODE_PRIVATE)
            val cUsername = user.getString(username + "_username", "")

            // Empty Fields
            if (name.isEmpty() || email.isEmpty() || bdate.isEmpty() || username.isEmpty() || pass.isEmpty() || cpass.isEmpty() || rgGender.checkedRadioButtonId == -1) {
                Toast.makeText(this, R.string.err_required_fields, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Existing Username
            if (username == cUsername) {
                tvErrUsername.visibility = View.VISIBLE
                hasError = true
            } else tvErrUsername.visibility = View.GONE

            // Invalid Email
            if (!isEmailValid(email)) {
                tvErrEmail.visibility = View.VISIBLE
                hasError = true
            } else tvErrEmail.visibility = View.GONE

            // Invalid Password
            if (!isPassValid(pass)) {
                tvErrPass.visibility = View.VISIBLE
                hasError = true
            } else tvErrPass.visibility = View.GONE

            // Password Mismatch
            if (cpass != pass) {
                tvErrPassMatch.visibility = View.VISIBLE
                hasError = true
            } else tvErrPassMatch.visibility = View.GONE

            if (hasError) {
                Toast.makeText(this, R.string.err_invalid_input, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                // Saving User Data
                val user = getSharedPreferences("users", MODE_PRIVATE)

                user.edit {
                    putString(username + "_name", name)
                    putString(username + "_email", email)
                    putString(username + "_gender", getGender(rgGender.checkedRadioButtonId))
                    putString(username + "_bdate", bdate)
                    putString(username + "_username", username)
                    putString(username + "_pass", pass)
                }
                Toast.makeText(this, R.string.new_acc_created, Toast.LENGTH_LONG).show()
                redirectPage(LoginActivity::class.java)
            }
        }

        // Redirects to Login Page
        tvLoginPage.setOnClickListener {redirectPage(LoginActivity::class.java)}
    }

    private fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isPassValid(pass: String): Boolean {
        if (pass.length < 8) return false
        if (!pass.any {it.isUpperCase()}) return false
        if (!pass.any {it.isDigit()}) return false
        if (!pass.any {!it.isLetterOrDigit()}) return false
        return true
    }

    private fun showDatePickerDialog(bday: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                bday.setText(dateFormat.format(selectedDate.time))
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun getGender(gender: Int): String = when (gender) {
        R.id.rbMale -> "Male"
        R.id.rbFemale -> "Female"
        R.id.rbOther -> "Other"
        else -> ""
    }

    private fun redirectPage(target: Class<*>) = startActivity(Intent(this@SignUpActivity, target))
}