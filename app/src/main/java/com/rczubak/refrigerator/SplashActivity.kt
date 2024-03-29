package com.rczubak.refrigerator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed(
            {startActivity(Intent(this@SplashActivity,MainActivity::class.java))},
            1000)
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

    }

    override fun onPause() {
        finish()
        return super.onPause()
    }

}
