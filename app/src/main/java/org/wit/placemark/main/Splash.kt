package org.wit.placemark.main

//TODO Search, Dropdown, Change phone number to Int

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.wit.placemark.R


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val actionBar = supportActionBar
        actionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@Splash, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}

