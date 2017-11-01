package com.ardaozceviz.flashchat

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

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()
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

        /*  // Check if password is empty
          if (TextUtils.isEmpty(password)){
              register_password.error = getString(R.string.error_field_required)
              focusView = register_password
              cancel = true
          }*/

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            Log.d("RegisterActivity", "Password Check")
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
        Log.d("RegisterActivity", "isEmailValid() executed")
        return email.contains("@")
    }

    fun isPasswordValid(password: String): Boolean {
        Log.d("RegisterActivity", "isPasswordValid() executed")
        val confirmPassword = register_confirm_password.text.toString()
        val result: Boolean
        return confirmPassword == password && password.length > 4
    }

    fun createFirebaseUser() {
        val email = register_email.text.toString()
        val password = register_password.text.toString()
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({ task: Task<AuthResult> ->
            Log.d("RegisterActivity", "createFirebaseUser(): ${task.isSuccessful}")
            Log.d("RegisterActivity", "createFirebaseUser(): $task.")
            if (!task.isSuccessful) {
                showErrorDialog(task.exception.toString())
                Log.d("RegisterActivity", "createFirebaseUser() is failed: ${task.exception}")
            }
        })
    }

    fun showErrorDialog(message: String) {
        object : AlertDialog.Builder(this) {}
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }
}
