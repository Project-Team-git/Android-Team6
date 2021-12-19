package com.example.doggee

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetListActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var preference: SharedPreferences
    private lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)
        val my_toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_toolbar)
        setSupportActionBar(my_toolbar)
        //ends


        //drawer layout code starts here
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val nav_view = findViewById<NavigationView>(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        //setting custom toolbar image
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.logof) //your icon here
        changeEmailDrawer(nav_view)
        //ends

        //on item click listener
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }

                R.id.myOrders -> {
                    val intent = Intent(this, MyInterestActivity::class.java)
                    startActivity(intent)
                }

                R.id.otherUsers -> {
                    val intent = Intent(this, OtherUsersActivity::class.java)
                    startActivity(intent)
                }

            }
            true

        }

        val apiclient = application as ApiClient
        val service = apiclient.apiService
        session = SessionManager(this)
        val intent = Intent(this, LoginActivity::class.java)

        val token = session.fetchAuthToken()
        val items: MutableList<Pets_Data> = mutableListOf<Pets_Data>()
        if (session.fetchAuthToken() != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val result = service.GetPets("Bearer $token")
                var i = 0
                withContext(Dispatchers.Main)
                {
                    if (result.isSuccessful) {
                        while (i < result.body()?.pets!!.size) {
                            items.add(result.body()?.pets!![i])
                            i += 1
                        }
                    } else {
                        startActivity(intent)
                    }
                    val recycle = findViewById<RecyclerView>(R.id.recycleView)
                    recycle.adapter = AdapterClass(items, applicationContext)
                    recycle.layoutManager = LinearLayoutManager(this@PetListActivity)
                }

            }
        } else
            startActivity(intent)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
    private fun changeEmailDrawer(nav_view: NavigationView) {
        val printemail = getSharedPreferences("user", Context.MODE_PRIVATE)
        val mail: String = printemail.getString("email", "").toString()

        val drawerEmailId = nav_view.getHeaderView(0).findViewById<TextView>(R.id.drawerEmailId)
        drawerEmailId.text = mail
    }

}