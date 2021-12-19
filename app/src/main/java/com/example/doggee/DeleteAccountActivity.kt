package com.example.doggee

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccountActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deleted_account)
        sessionManager = SessionManager(this)
        val showemail = getSharedPreferences("user", Context.MODE_PRIVATE)
        val mail2: String = showemail.getString("email", "").toString()
        findViewById<TextView>(R.id.mailShow).text=mail2
        findViewById<TextView>(R.id.delete).setOnClickListener{


            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as ApiClient
                val service=sampleApplication.apiService
                service.deleteUser("Bearer ${sessionManager.fetchAuthToken()}").enqueue(object :
                    Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {

                        if(response.isSuccessful)
                        {
                            Toast.makeText(applicationContext,"Your Account Successfully deleted", Toast.LENGTH_LONG).show()
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
                            val progressDialog= ProgressDialog(this@DeleteAccountActivity,R.style.PetAppDialogStyle)
                            progressDialog.setMessage("Loading...")
                            progressDialog.show()
                            val intent = Intent(this@DeleteAccountActivity,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else
                        {

                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()

                    }
                })
            }

        }

    }
}