package com.example.doggee

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)
        sessionManager = SessionManager(this)
        findViewById<TextView>(R.id.change).setOnClickListener {
            val printemail = getSharedPreferences("user", Context.MODE_PRIVATE)
            val mail: String = printemail.getString("email", "").toString()
            findViewById<TextInputEditText>(R.id.hold).hint = mail

            val newMail = findViewById<TextInputLayout>(R.id.newEmail).editText?.text.toString()
            if (newMail.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Please fill required field", Toast.LENGTH_LONG)
                    .show()
            } else {
                val userMail = EmailChangeRequest(newMail.toString())
                CoroutineScope(Dispatchers.IO).launch {
                    val sampleApplication = application as ApiClient
                    val service = sampleApplication.apiService
                    service.changeUserEMail(userMail, "Bearer ${sessionManager.fetchAuthToken()}")
                        .enqueue(object :
                            Callback<Void?> {
                            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                                if (response.isSuccessful) {
                                    val sharedPreferences =
                                        getSharedPreferences("user", Context.MODE_PRIVATE)
                                    val editor = sharedPreferences.edit()
                                    editor.apply()
                                    {
                                        putString("email", newMail)
                                    }.apply()
                                    val progressDialog = ProgressDialog(
                                        this@ChangeEmailActivity,
                                        R.style.PetAppDialogStyle
                                    )
                                    progressDialog.setMessage("Loading...")
                                    progressDialog.show()
                                    Toast.makeText(
                                        applicationContext,
                                        "Email changed successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(
                                        this@ChangeEmailActivity,
                                        ProfileActivity::class.java
                                    )
                                    startActivity(intent)


                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Email change has failed",
                                        Toast.LENGTH_LONG
                                    ).show()

                                }
                            }

                            override fun onFailure(call: Call<Void?>, t: Throwable) {
                                //  TODO("Not yet implemented")
                            }
                        })
                }
            }
        }
    }

}