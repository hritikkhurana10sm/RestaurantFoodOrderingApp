package com.hritik.foodhunt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.hritik.foodhunt.R

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var btnContinue: Button
    private lateinit var txtNo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)
        val total = intent.getStringExtra("total")
        txtNo = findViewById(R.id.txtNo)
        txtNo.text =
            "Your order will reach you on time.\nSit back at home and wait.\uD83D\uDC69\u200D\uD83C\uDF73\nKeep Rs. $total ready for smooth payment"
        btnContinue = findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener {
            val intent = Intent(this@OrderSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
            this@OrderSuccessActivity.finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@OrderSuccessActivity, HomeActivity::class.java)
        startActivity(intent)
        this@OrderSuccessActivity.finish()
    }
}
