package com.ardaozceviz.flashchat

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main_chat.*

class MainChatActivity : AppCompatActivity() {
    val TAG = "MainChatActivity"

    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: ChatListAdapter
    lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() is executed.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)
        setupUserName()
        // databaseReference represents a particular location in our cloud database
        // databaseReference is used for reading and writing data to that location in the database
        databaseReference = FirebaseDatabase.getInstance().reference

        // Send message when the "enter" button is pressed
        mainMessageEditText.setOnEditorActionListener { _, _, _ ->
            Log.d(TAG, "mainMessageEditText onClickListener is executed.")
            sendMessage()
            true
        }
    }

    override fun onStart() {
        super.onStart()
        val layoutManager = LinearLayoutManager(this)
        adapter = ChatListAdapter(this, databaseReference, username)
        mainMessagesRecyclerView.adapter = adapter
        mainMessagesRecyclerView.layoutManager = layoutManager

    }

    // onStop() gets called when the app is no longer visible to the user
    // good place to stop the resources that we don't need
    override fun onStop() {
        super.onStop()
        adapter.cleanup()

    }

    fun mainSendMessageButtonClicked(view: View) {
        Log.d(TAG, "mainSendMessageButtonClicked() is executed.")
        sendMessage()
    }

    fun sendMessage() {
        Log.d(TAG, "sendMessage() is executed.")
        val input: String = mainMessageEditText.text.toString()
        if (!input.isEmpty()) {
            val chat = InstantMessage(input, username)
            Log.d(TAG, "sendMessage() chat: $chat")
            databaseReference.child("messages").push().setValue(chat)
            mainMessageEditText.setText("")
        }
    }

    fun setupUserName() {
        Log.d(TAG, "setupUserName() is executed")
        val prefs = getSharedPreferences(CHAT_PREFS, Context.MODE_PRIVATE)
        username = prefs.getString(USER_NAME_KEY, null)
        Log.d(TAG, "prefs: $prefs")
        Log.d(TAG, "username: ${prefs.getString(USER_NAME_KEY, null)}")
        //if (main_chat_username.text.toString().isEmpty()) main_chat_username.text = "Anonymous"
    }
}
