package com.example.doggee

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        session = SessionManager(this)


        val printemail = getSharedPreferences("user", Context.MODE_PRIVATE)
        val mail: String = printemail.getString("email", "").toString()
        findViewById<TextView>(R.id.mailView).text=mail
        val memberView = findViewById<TextView>(R.id.memberView)
        val member = session.fetchMember()

        val sdf = SimpleDateFormat("dd-mm-yyyy")
        val netDate = Date(member)
        val displayThisDate = sdf.format(netDate)
        memberView.text="Member since: $displayThisDate"

        findViewById<TextView>(R.id.changeMail).setOnClickListener{
            val intent = Intent(this@ProfileActivity, ChangeEmailActivity::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.deleteBtn).setOnClickListener{
            val intent = Intent(this@ProfileActivity, DeleteAccountActivity::class.java)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.logoutBtn).setOnClickListener{
            val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            finish()
            val local = getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor2 = local.edit()
            editor2.clear()
            editor2.apply()
            finish()
            val intent = Intent(this@ProfileActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<TextView>(R.id.history).setOnClickListener{
            val intent = Intent(this@ProfileActivity, LoginHistoryActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.imagePro).setOnClickListener()
        {

            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,100)

        }

    }
    override fun onBackPressed() {
        val intent = Intent(this, PetListActivity::class.java)
        startActivity(intent)
    }

}