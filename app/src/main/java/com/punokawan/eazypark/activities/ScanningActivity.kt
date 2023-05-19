package com.punokawan.eazypark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.punokawan.eazypark.databinding.ActivityScanningBinding

class ScanningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScanningBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportActionBar?.hide()

        binding.cancelButton.setOnClickListener {
            backToHistory()
        }

        binding.backBtn.setOnClickListener {
            backToHistory()
        }

        moveToNextActivityTemp()

    }

    private fun moveToNextActivityTemp(){
        val timeout:Long = 3000

        Handler().postDelayed({
            val intent = Intent(this@ScanningActivity,ParkingTicketActivity::class.java)
            startActivity(intent)

            // finish() to close activity to prevent the user from returning to it
        },timeout)
    }

    private fun backToHistory(){
        var intent = Intent(this@ScanningActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}