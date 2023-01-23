package com.example.openweathermapapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(val countries: Array<String>) : RecyclerView.Adapter<CityAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun OnItemClick(country : String)
    }

    var itemClickListener : OnItemClickListener ?= null

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
        init {
            textView.setOnClickListener {
                itemClickListener?.OnItemClick(countries[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = countries[position]
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}