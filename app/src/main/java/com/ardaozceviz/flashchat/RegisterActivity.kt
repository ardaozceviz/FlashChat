package com.ardaozceviz.flashchat

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    val TAG = "RegisterActivity" +
            ""
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Executed when Sign Up button is pressed.
    fun signUp(v: View) {
        Log.d(TAG, "signUp() is executed.")
        attemptRegistration()
    }

    private fun attemptRegistration() {
        Log.d(TAG, "attemptRegistration() is executed.")

        // Reset errors displayed in the form.
        register_email.error = null
        register_password.error = null

        // Store values at the time of the login attempt.
        val email = register_email.text.toString()
        val password = register_password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            Log.d(TAG, "Password check")
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
            createFirebaseUser()
        }
    }

    fun isEmailValid(email: String): Boolean {
        Log.d(TAG, "isEmailValid() is executed.")
        return email.contains("@")
    }

    fun isPasswordValid(password: String): Boolean {
        Log.d(TAG, "isPasswordValid() is executed.")
        val confirmPassword = register_confirm_password.text.toString()
        val result: Boolean
        return confirmPassword == password && password.length > 4
    }

    fun createFirebaseUser() {
        val email = register_email.text.toString()
        val password = register_password.text.toString()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({ task: Task<AuthResult> ->
            Log.d(TAG, "createFirebaseUser(): ${task.isSuccessful}")
            Log.d(TAG, "createFirebaseUser() task: $task.")
            if (!task.isSuccessful) {
                showErrorDialog(task.exception.toString())
                Log.d(TAG, "createFirebaseUser() is failed: ${task.exception}")
            } else {
                saveUserName()
                val intent = Intent(this, LoginActivity::class.java)
                finish()
                startActivity(intent)
            }
        })
    }

    fun showErrorDialog(message: String) {
        Log.d(TAG, "showErrorDialog() is executed.")
        AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

    fun saveUserName() {
        Log.d(TAG, "saveUserName() is executed.")
        val username = register_username.text.toString()
        val prefs: SharedPreferences = getSharedPreferences(CHAT_PREFS, 0)
        prefs.edit().putString(USER_NAME_KEY, username).apply()

    }
}
