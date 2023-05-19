package com.punokawan.eazypark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.punokawan.eazypark.R
import com.punokawan.eazypark.databinding.ActivityParkingTicketBinding

class ParkingTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingTicketBinding
    private lateinit var ticketTextView: TextView
    private lateinit var spotTextView: TextView
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParkingTicketBinding.inflate(layoutInflater)
        FirebaseApp.initializeApp(this)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backBtn.setOnClickListener {
            backBtn()
        }

        binding.parkingDetailsBtn.setOnClickListener {
            moveToParkingDetails()
        }
        ticketTextView = findViewById(R.id.ticket_id_tv)
        spotTextView = findViewById(R.id.parking_spot_tv)
        firestore = FirebaseFirestore.getInstance()
        retrieveTicketFromFirestore()
    }
    private fun retrieveTicketFromFirestore() {
        val usersCollection = firestore.collection("users")
        val userDocumentId = "5FFEhRgBThMzuYU8NjUP" // Replace with the actual user document ID
        val userDocumentRef = usersCollection.document(userDocumentId)
        val parkingTicketCollection = userDocumentRef.collection("parking ticket")
        val parkingTicketDocumentId = "v3ogVowHCiwPavtJTy4r" // Replace with the desired parking ticket document ID
        val parkingTicketDocumentRef = parkingTicketCollection.document(parkingTicketDocumentId)

        parkingTicketDocumentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val ticket = documentSnapshot.getString("ticket")
                    val parkingSpot = documentSnapshot.getString("parkingSpot")

                    ticket?.let {
                        ticketTextView.text = it
                    }
                    parkingSpot?.let {
                        spotTextView.text = it
                    }
                } else {
                    ticketTextView.text = "Not found"
                    spotTextView.text = "Not found"
                }
            }
            .addOnFailureListener { exception ->
                ticketTextView.text = "Failed to retrieve ticket"
                spotTextView.text = "Failed to retrieve parking spot"
                Log.e(MainActivity.TAG, "Error retrieving ticket", exception)
            }
    }
    private fun moveToParkingDetails(){
        val intent = Intent(this@ParkingTicketActivity,ParkingDetailsActivity::class.java)
        startActivity(intent)
    }

    private fun backBtn(){
        val intent = Intent(this@ParkingTicketActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}