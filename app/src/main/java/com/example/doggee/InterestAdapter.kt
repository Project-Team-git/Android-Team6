package com.example.doggee

import android.annotation.SuppressLint
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InterestAdapter(var items:MutableList<Petss_Data>, var context: Context, var application: Application):
    RecyclerView.Adapter<InterestAdapter.ViewHolder>() {
  //  lateinit var sharedPreferenceManager: SharedPreferenceManager
  private lateinit var sessionManager: SessionManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.interest_item,parent,false)
        return ViewHolder(view)

    }
    override fun getItemCount(): Int {
        return items.size
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items!=null)
            Picasso.get().load(items[position]!!.url).into(holder.img)
        holder.names.text=items[position].name
        holder.type.text=items[position].type
        if(items[position].age.toString().toInt()==1)
            holder.agee.text=items[position].age.toString()+"year"
        else
            holder.agee.text=items[position].age.toString()+"years"
        holder.delbtn.setOnClickListener{
            sessionManager = SessionManager(context)
            CoroutineScope(Dispatchers.IO).launch {
                val sampleApplication=application as ApiClient
                val service=sampleApplication.apiService
                service.deleteInterest(items[position].interestId,"Bearer ${sessionManager.fetchAuthToken()}").enqueue(object : Callback<Void?> {
                    override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                        if(response.isSuccessful)
                        {
                            val progressDialog= ProgressDialog(context,R.style.PetAppDialogStyle)
                            progressDialog.setMessage("Loading...")
                            progressDialog.show()
                            val intent = Intent(context,MyInterestActivity::class.java)
                            ContextCompat.startActivity(context, intent, Bundle())


                        }
                        else{
                            Toast.makeText(context,"Sorry could not delete", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void?>, t: Throwable) {
                        Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
                    }
                })
            }

        }

    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.intimage)
        var names=itemView.findViewById<TextView>(R.id.intname)
        var type=itemView.findViewById<TextView>(R.id.inttype)
        var agee=itemView.findViewById<TextView>(R.id.intage)
        var delbtn=itemView.findViewById<TextView>(R.id.delint)
     //   var btn = itemView.findViewById<Button>(R.id.sellBtn)

//        init {
//
//            delbtn.setOnClickListener{
//                val newPosition: Int = getAdapterPosition()
//                sessionManager = SessionManager(context)
//               // var pos = adapterPosition
//                CoroutineScope(Dispatchers.IO).launch {
//                    val sampleApplication=application as ApiClient
//                    val service=sampleApplication.apiService
//                    service.deleteInterest(items[newPosition].id,"Bearer ${sessionManager.fetchAuthToken()}").enqueue(object : Callback<Void?> {
//                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
//                            if(response.isSuccessful)
//                            {
//                                var intent = Intent(context,MyInterestActivity::class.java)
//                                ContextCompat.startActivity(context, intent, Bundle())
//
//                            }
//                            else{
//                                Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<Void?>, t: Throwable) {
//                            Toast.makeText(context,"Something went wrong", Toast.LENGTH_LONG).show()
//                        }
//                    })
//                }
//
//            }
//
//        }
        //delbtn.setOnClickListener
//        init {
//            btn.setOnClickListener {
//                var pos = adapterPosition
//                var sessionManager = SharedPreferenceManager(context)
//                var Api = application as StockApplication
//                var token = sessionManager.fetchAuthToken()
//                var intent = Intent(context,SellStocksActivity::class.java)
//                intent.putExtra("owningId", items[pos].owningId)
//                ContextCompat.startActivity(context, intent, Bundle())
//
//            }
        }
    }
