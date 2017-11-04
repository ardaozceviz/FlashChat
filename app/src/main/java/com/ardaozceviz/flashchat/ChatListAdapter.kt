package com.ardaozceviz.flashchat

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

/**
 * Created by arda on 04/11/2017.
 */

class ChatListAdapter(private val activity: Activity, databaseReference: DatabaseReference, private val author: String) : RecyclerView.Adapter<ChatListAdapter.ChatInfoHolder>() {
    private val TAG = "ChatListAdapter"

    private val snapshotList = ArrayList<DataSnapshot?>()
    private var ref: DatabaseReference? = null

    private val childEventListener = object : ChildEventListener {
        // This function will be trigger each time when there is a new message
        override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
            Log.d(TAG, "childEventListener -> onChildAdded() is executed.")
            snapshotList.add(dataSnapshot)
            // After each addition to the ArrayList
            // We have to notify the list view to refresh itself because there is a new information available
            notifyDataSetChanged()
        }

        override fun onCancelled(p0: DatabaseError?) {
        }

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

        }

        override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
        }

        override fun onChildRemoved(p0: DataSnapshot?) {
        }
    }

    init {
        Log.d(TAG, "init{} is executed.")
        ref = databaseReference.child("messages")
        ref?.addChildEventListener(childEventListener)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount() is executed.")
        Log.d(TAG, "getItemCount(): ${snapshotList.size}.")
        return snapshotList.count()
    }

    override fun onBindViewHolder(holder: ChatInfoHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder() is executed.")
        val snapshot = snapshotList[position]
        val instantMessage = snapshot?.getValue(InstantMessage::class.java)
        if (instantMessage != null) {
            holder?.bindChatItem(instantMessage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatInfoHolder {
        Log.d(TAG, "onCreateViewHolder() is executed.")
        val view = LayoutInflater.from(activity).inflate(R.layout.chat_list_item, parent, false)
        return ChatInfoHolder(view)
    }

    inner class ChatInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val author = itemView.findViewById<TextView>(R.id.chatListItemAuthor)
        private val message = itemView.findViewById<TextView>(R.id.chatListItemMessage)

        fun bindChatItem(instantMessage: InstantMessage) {
            Log.d(TAG, "bindChatItem() is executed.")
            author.text = instantMessage.author
            message.text = instantMessage.messsage
        }
    }

    fun cleanup() {
        Log.d(TAG, "cleanup() is executed.")
        ref?.removeEventListener(childEventListener)
    }
}