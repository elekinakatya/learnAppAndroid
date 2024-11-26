package com.example.learnapp.list

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnapp.R
import com.example.learnapp.data.data

class MyAdapter(private var dataList: List<data>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(dataItem: data)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(dataItem: data, listener: OnItemClickListener) {
            nameTextView.text = dataItem.name
            itemView.setOnClickListener {
                listener.onItemClick(dataItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = dataList[position]
        holder.bind(dataItem, itemClickListener)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newDataList: List<data>) {
        dataList = newDataList
        notifyDataSetChanged()
    }
}