package com.example.doggee

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterClass(var songs:MutableList<Pets_Data>,val applicationContext: Context): RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.item,parent,false)
        return ViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dName.text=songs[position].name
       holder.dType.text=songs[position].type
        //holder.dYear.text=songs[position].age.toString()+"year"
        Picasso.get().load(songs[position].url).into(holder.dImage)
        if(songs[position].age==1)
        {
            holder.dYear.text=songs[position].age.toString()+"year"
        }
        else
            holder.dYear.text=songs[position].age.toString()+"years"

        holder.itemView.setOnClickListener{
            val int2 = Intent(applicationContext, PetDetaileActivity::class.java)
            int2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            int2.putExtra("dname", songs[position].name)
            int2.putExtra("dId", songs[position].id)
            int2.putExtra("dImg",songs[position].url)
            int2.putExtra("dVaccine", songs[position].vaccinated)
            int2.putExtra("dAge",songs[position].age)

            applicationContext.startActivity(int2)
        }

    }

    override fun getItemCount(): Int {
        return songs.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val dImage=itemView.findViewById<ImageView>(R.id.dogimage)
        val dName=itemView.findViewById<TextView>(R.id.dogname)
        val dType=itemView.findViewById<TextView>(R.id.dogtype)
       val dYear=itemView.findViewById<TextView>(R.id.dogage)
    }

}