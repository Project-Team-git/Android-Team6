package com.example.doggee

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyInterestActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_interest)

        sessionManager = SessionManager(this)
        val stocks:MutableList<Petss_Data> = mutableListOf<Petss_Data>()
        val token=sessionManager.fetchAuthToken()
        val nothing=findViewById<TextView>(R.id.dialog)



        CoroutineScope(Dispatchers.IO).launch{
            val apiclient=application as ApiClient
            val service=apiclient.apiService
            val result=service.getMyInterest("Bearer $token")
            if(result.isSuccessful) {

                var i = 0
                while(i<result.body()!!.petInterests!!.size){
                    val res=service.GetPets("Bearer $token")
                    var j=0
                    while(j<res.body()!!.pets!!.size){
                        if(result.body()!!.petInterests!![i].petId==res.body()!!.pets!![j].id)
                            stocks.add(Petss_Data(res.body()!!.pets!![j].id,res.body()!!.pets!![j].url,res.body()!!.pets!![j].name,res.body()!!.pets!![j].type,res.body()!!.pets!![j].age,result.body()!!.petInterests!![i].interestId))

                        j+=1

                    }
                    i+=1
                }

            }
            withContext(Dispatchers.Main){
                if(stocks.isNullOrEmpty())
                {
                    nothing.text="You are not interested in a pet yet"
                }
                else
                {




                    val recycle = findViewById<RecyclerView>(R.id.recycleViewInt)
                    recycle.adapter = InterestAdapter(stocks, this@MyInterestActivity, application)
                    recycle.layoutManager = LinearLayoutManager(this@MyInterestActivity)

                }
            }
        }


    }
    override fun onBackPressed() {
        val intent = Intent(this, PetListActivity::class.java)
        startActivity(intent)
    }
    }
