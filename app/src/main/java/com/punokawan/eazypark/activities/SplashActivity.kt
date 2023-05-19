package com.punokawan.eazypark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.punokawan.eazypark.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.startBtn.setOnClickListener {
            moveToHome()
        }

    }

    private fun moveToHome(){
        var intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}