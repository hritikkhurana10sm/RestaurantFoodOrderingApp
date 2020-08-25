package com.hritik.foodhunt.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.hritik.foodhunt.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //to display the next screen within 1 second and this screen acts like a Welcome Screen
        Handler().postDelayed(
            {
                val startActivity = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(startActivity)
            },1000)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
