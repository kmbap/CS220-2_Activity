package com.example.cs220_activity1_signup

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.method.*
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import java.text.SimpleDateFormat
import java.util.*

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

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbPassword = findViewById<CheckBox>(R.id.cbPass)
        val tvErrPass = findViewById<TextView>(R.id.tvErrPass)

        val etCPass = findViewById<EditText>(R.id.etCPass)
        val tvErrPassMatch = findViewById<TextView>(R.id.tvErrPassMatch)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)

        // Verifies Email
        etEmail.onFocusChangeListener = View.OnFocusChangeListener {view, hasFocus ->
            val email = etEmail.text.toString()

            if (!hasFocus) {
                if (isEmailValid(email)) {
                    tvErrEmail.visibility = View.GONE
                } else tvErrEmail.visibility = View.VISIBLE
            } else tvErrEmail.visibility = View.GONE
        }

        // Display Calendar
        etBirthdate.setOnClickListener {showDatePickerDialog(etBirthdate)}

        // Verifies Password
        etPassword.onFocusChangeListener = View.OnFocusChangeListener {view, hasFocus ->
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
        etCPass.onFocusChangeListener = View.OnFocusChangeListener {view, hasFocus ->
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

        // Submit Form
        btnSignUp.setOnClickListener {
            var hasError: Boolean = false

            val name = etFullName.text.toString()
            val email = etEmail.text.toString()
            val bdate = etBirthdate.text.toString()
            val pass = etPassword.text.toString()
            val cpass = etCPass.text.toString()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty() || bdate.isEmpty() || rgGender.checkedRadioButtonId == -1) {
                Toast.makeText(this, R.string.err_required_fields, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else if (!isEmailValid(email)) {
                tvErrEmail.visibility = View.VISIBLE
                hasError = true
            } else if (!isPassValid(pass)) {
                tvErrPass.visibility = View.VISIBLE
                hasError = true
            } else if (cpass != pass) {
                tvErrPassMatch.visibility = View.VISIBLE
                hasError = true
            }


            if (hasError) {
                Toast.makeText(this, R.string.err_invalid_input, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            } else {
                tvErrEmail.visibility = View.GONE
                tvErrPass.visibility = View.GONE
                tvErrPassMatch.visibility = View.GONE
                Toast.makeText(this, R.string.new_acc_created, Toast.LENGTH_LONG).show()
            }
        }
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
}