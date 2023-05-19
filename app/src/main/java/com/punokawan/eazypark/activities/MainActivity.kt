package com.punokawan.eazypark.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.punokawan.eazypark.R
import com.punokawan.eazypark.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var welcomeTextView: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        supportActionBar?.hide()

        binding.scanIconContainer.setOnClickListener {
            moveToScan()
        }

        welcomeTextView = findViewById(R.id.user_welcome_tv)
        firestore = FirebaseFirestore.getInstance()

        retrieveUsernameFromFirestore()
    }

    private fun retrieveUsernameFromFirestore() {
        val documentId = "5FFEhRgBThMzuYU8NjUP" // Replace with your actual document ID
        val usersCollection = firestore.collection("users")
        val documentRef = usersCollection.document(documentId)

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val username = documentSnapshot.getString("username")
                    username?.let {
                        welcomeTextView.text = "Hello, $username"
                    }
                } else {
                    welcomeTextView.text = "User not found"
                }
            }
            .addOnFailureListener { exception ->
                welcomeTextView.text = "Failed to retrieve username"
                Log.e(TAG, "Error retrieving username", exception)
            }
    }

    private fun moveToScan() {
        val intent = Intent(this@MainActivity, ScanningActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}