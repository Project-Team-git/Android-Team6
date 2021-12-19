package com.example.doggee

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var sharedPreference : SharedPreferences
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sessionManager = SessionManager(this)
        supportActionBar?.hide()


        val regButton = findViewById<TextView>(R.id.RegButton)

        regButton.setOnClickListener {


            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Pleas fill all field", Toast.LENGTH_LONG).show()
            } else {

            val user = LoginRequest(email.toString(), password.toString())


            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication = application as ApiClient
                val service = sampleApplication.apiService
                service.RegUser(user).enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if (response.isSuccessful) {
                            service.login(user).enqueue(object : Callback<User?> {
                                override fun onResponse(
                                    call: Call<User?>,
                                    response: Response<User?>
                                ) {
                                    if (response.isSuccessful) {

                                        val sharedPreferences =
                                            getSharedPreferences("user", Context.MODE_PRIVATE)
                                        val editor = sharedPreferences.edit()
                                        editor.apply()
                                        {
                                            putString("email", response.body()!!.email)
                                            putBoolean("loginStatus", true)
                                        }

                                            .apply()
                                        sessionManager.saveAuthToken(response.body()?.token.toString())
                                        sessionManager.saveMember(response.body()!!.memberSince)
                                        val progressDialog=ProgressDialog(this@RegisterActivity,R.style.PetAppDialogStyle)
                                        progressDialog.setMessage("Loading...")
                                        progressDialog.show()
                                        val intent = Intent(
                                            this@RegisterActivity,
                                            PetListActivity::class.java
                                        )
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "User could not login",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<User?>, t: Throwable) {

                                    Toast.makeText(
                                        applicationContext,
                                        "User could not login",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            })

                        } else {
                            //  Toast.makeText(applicationContext,"LoginFailure", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {

                        // Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
        }


    }
