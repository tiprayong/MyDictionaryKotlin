package com.comtip.tip.weblogkt01

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.comtip.tip.mydictk.R
import com.comtip.tip.mydictk.RoomDatabaseKotlin.Vocabulary


/**
 * Created by TipRayong on 14/3/2561 13:21
 * WebLogKT01
 *
 */
class CustomRecyclerViewAdapter(val context: Context, private val newsList: List<Vocabulary>)
    : RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_custom, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.textViewCustom.text = newsList.get(position).vocab


        val listener = View.OnClickListener { onItemClickListener!!.onItemClick(position) }

        val listener2 = View.OnLongClickListener {
            onItemLongClickListener!!.onItemLongClick(position)
            false
        }

        holder.textViewCustom.setOnClickListener(listener)
        holder.textViewCustom.setOnLongClickListener(listener2)

    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewCustom: TextView = itemView.findViewById<View>(R.id.textviewCustom) as TextView
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }


}
