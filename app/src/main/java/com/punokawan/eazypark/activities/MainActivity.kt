package com.punokawan.eazypark.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.punokawan.eazypark.HistoryListAdapter
import com.punokawan.eazypark.R
import com.punokawan.eazypark.databinding.ActivityMainBinding
import com.punokawan.eazypark.dummydata.HistoryData


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var welcomeTextView: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.parkingHistoryRv.layoutManager = layoutManager

        FirebaseApp.initializeApp(this)

        supportActionBar?.hide()

        binding.scanIconContainer.setOnClickListener {
            moveToScan()
        }

        showDummyHistoryData(getDummyHistoryData())

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

    private fun showDummyHistoryData(data:ArrayList<HistoryData>){
        val adapter = HistoryListAdapter(data)
        binding.parkingHistoryRv.adapter = adapter
    }

    private fun getDummyHistoryData():ArrayList<HistoryData>{
        var historyData:ArrayList<HistoryData> = ArrayList()
        val data = HistoryData(
            location = "Sun Plaza",
            date = "22 May 2023",
            timeIn = "14:30",
            timeOut = "16:30",
            parking_time = "4 Hours of Parking"
        )

        for( i in 0 .. 9){
            historyData.add(data)
        }
        return historyData
    }

    private fun moveToScan() {
        val intent = Intent(this@MainActivity, ScanningActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val TAG = "MainActivity"
    }
}