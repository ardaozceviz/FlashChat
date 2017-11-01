package com.ardaozceviz.flashchat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*login_password.setOnEditorActionListener { textView, id, keyEvent ->
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }*/
    }

    // Executed when Sign in button pressed
    fun signInExistingUser(view: View) {
        Log.d("Login", "Sign in button pressed")
    }

    // Executed when Register button pressed
    fun registerNewUser(view: View) {
        Log.d("Login", "Register button pressed")

    }

    fun attemptLogin() {
        Log.d("Login", "attemptLogin() executed")

    }
}
