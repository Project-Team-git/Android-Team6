package com.example.doggee

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OtherUsersActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_users)
        sessionManager = SessionManager(this)
        val apiclient = application as ApiClient
        val service=apiclient.apiService


        val token = sessionManager.fetchAuthToken()
        val items: MutableList<otherUserDataClass> = mutableListOf<otherUserDataClass>()
        if (sessionManager.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = service.otherUserDetails("Bearer $token")
                var i = 0
                if (result.isSuccessful) {
                    while (i < result.body()?.users!!.size) {
                        items.add(result.body()?.users!![i])
                        i += 1
                    }
                } else {
                    startActivity(intent)
                }
                withContext(Dispatchers.Main) {
                    val recycle = findViewById<RecyclerView>(R.id.otherRecycle)
                    recycle.adapter = OtherUserAdapterClass(items,this@OtherUsersActivity)
                    recycle.layoutManager = LinearLayoutManager(this@OtherUsersActivity)
                }

            }
        } else {
            Toast.makeText(
                this@OtherUsersActivity,
                "Not Available!!!",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, PetListActivity::class.java)
        startActivity(intent)
    }
}