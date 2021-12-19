package com.example.doggee

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class LoginHistoryAdapter(var logsVal:MutableList<LoginHistoryDataClass>, var context: Context):
    RecyclerView.Adapter<LoginHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginHistoryAdapter.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.logins,parent,false)
        return ViewHolder(view)

    } override fun getItemCount(): Int {
        return logsVal.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val logindate=itemView.findViewById<TextView>(R.id.LoginDates)

    }

    @SuppressLint("SimpleDateFormat", "WeekBasedYear")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("dd-MMM-YYYY")
        val netDate = Date(logsVal[position].loginTimestamp)
        val displayThisDate = sdf.format(netDate)

        holder.logindate.text= displayThisDate

    }
}