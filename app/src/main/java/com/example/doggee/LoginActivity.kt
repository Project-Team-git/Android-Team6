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

class LoginActivity : AppCompatActivity() {
    lateinit var sharedPreference : SharedPreferences
    private lateinit var sessionManager: SessionManager
   // private lateinit var mProgress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        supportActionBar?.hide()

        findViewById<TextView>(R.id.regBtn).setOnClickListener {

            val newScreenIntent = Intent(this, RegisterActivity::class.java)

            startActivity(newScreenIntent)
        }
        val loginButton = findViewById<TextView>(R.id.LogButton)

        loginButton.setOnClickListener {


            val email = findViewById<TextInputLayout>(R.id.email).editText?.text
            val password = findViewById<TextInputLayout>(R.id.password).editText?.text
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Pleas fill all field", Toast.LENGTH_LONG).show()
            } else {


            val user = LoginRequest(email.toString(), password.toString())
            // val token=sharedPreferences.getString("token",null)

            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication = application as ApiClient
                val service = sampleApplication.apiService
                service.login(user).enqueue(object : Callback<User?> {
                    override fun onResponse(call: Call<User?>, response: Response<User?>) {
                        if (response.isSuccessful) {
                          //  mProgress.dismiss();

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

                            val progressDialog=ProgressDialog(this@LoginActivity,R.style.PetAppDialogStyle)
                            progressDialog.setMessage("Loading...")
                            progressDialog.show()
                            val intent = Intent(this@LoginActivity, PetListActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                          //  mProgress.dismiss();
                            Toast.makeText(
                                applicationContext,
                                "User could not login",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<User?>, t: Throwable) {
                       // mProgress.dismiss();
                        Toast.makeText(
                            applicationContext,
                            "User could not login",
                            Toast.LENGTH_LONG
                        ).show()

                    }
                })
            }

        }
    }
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}