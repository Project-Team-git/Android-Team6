package com.example.doggee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val preference =  getSharedPreferences("user", Context.MODE_PRIVATE)
        val islogin=preference.getBoolean("loginStatus",false)
        Handler().postDelayed({

            if(islogin) {
                val intent = Intent(this@MainActivity, PetListActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }


        },3000)
    }
}