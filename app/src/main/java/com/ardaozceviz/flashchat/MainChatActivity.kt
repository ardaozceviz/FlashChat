package com.ardaozceviz.flashchat

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main_chat.*

class MainChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)
        setupUserName()
    }

    fun setupUserName() {
        Log.d("MainChatActivity", "setupUserName is executed")
        val prefs = getSharedPreferences(CHAT_PREFS, Context.MODE_PRIVATE)
        Log.d("MainChatActivity", "prefs: $prefs")
        Log.d("MainChatActivity", "username: ${prefs.getString(USER_NAME_KEY, null)}")
        main_chat_username.text = prefs.getString(USER_NAME_KEY, null)
        if (main_chat_username.text.toString().isEmpty()) main_chat_username.text = "Anonymous"
    }
}
