package com.example.doggee


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class LoginHistoryActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var sessionManager: SessionManager
    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_history)

            val memberdate=findViewById<TextView>(R.id.MemberDate)
        sessionManager = SessionManager(this)
            val member= sessionManager.fetchMember()

            val sdf = SimpleDateFormat("dd-MMM-YYYY")
            val netDate = Date(member)
            val displayThisDate = sdf.format(netDate)
            memberdate.text= displayThisDate



            val apiclient = application as ApiClient
        val service=apiclient.apiService
         //   sharedPreference = SharedPreferenceManager(this)
           // var intent= Intent(this@LoginHistoryActivity,profileActivity::class.java)

            val token = sessionManager.fetchAuthToken()
            val items: MutableList<LoginHistoryDataClass> = mutableListOf<LoginHistoryDataClass>()
            if (sessionManager.fetchAuthToken() != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    val result = service.logDetails("Bearer $token")
                    var i = 0
                    if (result.isSuccessful) {
                        while (i < result.body()?.loginEntries!!.size) {
                            items.add(result.body()?.loginEntries!![i])
                            i += 1
                        }
                    } else {
                        startActivity(intent)
                    }
                    withContext(Dispatchers.Main) {
                        val recycle = findViewById<RecyclerView>(R.id.loginHistoryRecycler)
                        recycle.adapter = LoginHistoryAdapter(items,this@LoginHistoryActivity)
                        recycle.layoutManager = LinearLayoutManager(this@LoginHistoryActivity)
                    }

                }
            } else {
                Toast.makeText(
                    this@LoginHistoryActivity,
                    "Not Available!!!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
