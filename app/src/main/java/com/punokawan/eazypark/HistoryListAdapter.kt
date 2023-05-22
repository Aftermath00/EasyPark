package com.punokawan.eazypark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.punokawan.eazypark.dummydata.HistoryData

class HistoryListAdapter(private val listHistory:ArrayList<HistoryData>):RecyclerView.Adapter<HistoryListAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryListAdapter.ListViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.parking_history_item,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryListAdapter.ListViewHolder, position: Int) {
        val data = listHistory[position]

        holder.location.text = data.location
        holder.date.text = data.date
        holder.parking_time.text = data.parking_time
        holder.timeIn.text = data.timeIn
        holder.timeOut.text = data.timeOut
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    class ListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val location:TextView = itemView.findViewById(R.id.parking_location_tv)
        val date: TextView = itemView.findViewById(R.id.parking_date_tv)
        val parking_time:TextView = itemView.findViewById(R.id.parking_time)
        val timeIn:TextView = itemView.findViewById(R.id.time_begin_tv)
        val timeOut:TextView = itemView.findViewById(R.id.time_end_tv)
    }

}