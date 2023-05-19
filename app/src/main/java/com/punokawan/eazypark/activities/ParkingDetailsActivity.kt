package com.punokawan.eazypark.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swipebutton_library.SwipeButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.punokawan.eazypark.R
import com.punokawan.eazypark.databinding.ActivityParkingDetailsBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ParkingDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkingDetailsBinding
    private lateinit var ticketTextView: TextView
    private lateinit var spotTextView: TextView
    private lateinit var enterTimeTextView: TextView
    private lateinit var outTimeTextView: TextView
    private lateinit var feeTextView: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParkingDetailsBinding.inflate(layoutInflater)
        FirebaseApp.initializeApp(this)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.backBtn.setOnClickListener {
            backBtn()
        }

        val swipeButton = findViewById<SwipeButton>(R.id.swipe_btn)
        swipeButton.setOnActiveListener {
            Toast.makeText(
                applicationContext,
                "Activated",
                Toast.LENGTH_SHORT
            ).show()
        }

        ticketTextView = findViewById(R.id.parking_id_tv)
        spotTextView = findViewById(R.id.parking_spot_tv)
        enterTimeTextView = findViewById(R.id.enter_time_tv)
        outTimeTextView = findViewById(R.id.out_time_tv)
        feeTextView = findViewById(R.id.parking_rate_tv)
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
                    val enterTime = documentSnapshot.getTimestamp("enterTime")
                    val outTime = documentSnapshot.getTimestamp("outTime")
                    val fee = documentSnapshot.getDouble("fee")

                    ticket?.let {
                        ticketTextView.text = it
                    }
                    parkingSpot?.let {
                        spotTextView.text = it
                    }
                    enterTime?.let { enterTimeTimestamp ->
                        outTime?.let { outTimeTimestamp ->
                            val durationMillis = outTimeTimestamp.toDate().time - enterTimeTimestamp.toDate().time
                            val durationHours = TimeUnit.MILLISECONDS.toHours(durationMillis)
                            val fee = durationHours * 5000 // 5,000 IDR per hour

                            val enterTimeFormatted = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(enterTimeTimestamp.toDate())
                            enterTimeTextView.text = enterTimeFormatted

                            val outTimeFormatted = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(outTimeTimestamp.toDate())
                            outTimeTextView.text = outTimeFormatted

                            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
                            currencyFormat.currency = Currency.getInstance("IDR") // Set the currency to Indonesian Rupiah
                            val feeFormatted = currencyFormat.format(fee)
                            feeTextView.text = feeFormatted
                        }
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

    private fun backBtn() {
        val intent = Intent(this@ParkingDetailsActivity, ParkingTicketActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
