package com.ardaozceviz.flashchat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

    }

    // Executed when Sign in button pressed
    fun signInExistingUser(view: View) {
        Log.d("LoginActivity", "Sign in button pressed")
        val email = login_email.text.toString()
        val password = login_password.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        } else {
            Toast.makeText(this, " Logging", Toast.LENGTH_LONG).show()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener({ task: Task<AuthResult> ->
                Log.d("LoginActivity", "signInWithEmailAndPassword onComplete: ${task.isSuccessful}")
                if (!task.isSuccessful) {
                    Log.d("LoginActivity", "signInWithEmailAndPassword onComplete: ${task.exception}")
                    showErrorDialog(task.exception.toString())
                } else {
                    val intent = Intent(this, MainChatActivity::class.java)
                    finish()
                    startActivity(intent)
                }
            })
        }
    }

    fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

    // Executed when Register button pressed
    fun registerNewUser(view: View) {
        Log.d("LoginActivity", "Register button pressed")
        val intent = Intent(this, RegisterActivity::class.java)
        finish()
        startActivity(intent)
    }
}
