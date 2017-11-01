package com.ardaozceviz.flashchat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    // Executed when Sign Up button is pressed.
    fun signUp(v: View) {
        Log.d("RegisterActivity", "signUp() executed")
        attemptRegistration()
    }

    private fun attemptRegistration() {
        Log.d("RegisterActivity", "attemptRegistration() executed")

        // Reset errors displayed in the form.
        register_email.error = null
        register_password.error = null

        // Store values at the time of the login attempt.
        val email = register_email.text.toString()
        val password = register_password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            register_password.error = getString(R.string.error_invalid_password)
            focusView = register_password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            register_email.error = getString(R.string.error_field_required)
            focusView = register_email
            cancel = true
        } else if (!isEmailValid(email)) {
            register_email.error = getString(R.string.error_invalid_email)
            focusView = register_email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView!!.requestFocus()
        } else {
            // TODO: Call create FirebaseUser() here

        }
    }

    fun isEmailValid(email: String): Boolean {
        Log.d("RegisterActivity", "isEmailValid() executed")
        return email.contains("@")
    }

    fun isPasswordValid(password: String): Boolean {
        Log.d("RegisterActivity", "isPasswordValid() executed")
        return true
    }
}
