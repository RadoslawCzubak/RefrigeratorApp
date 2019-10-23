package com.rczubak.refrigerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    public lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        super.onDestroy()

        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }
}
