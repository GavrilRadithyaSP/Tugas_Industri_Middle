package com.gavril.midapps.friend_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gavril.midapps.R
import com.gavril.midapps.friend_app.database.entity.FriendEntity

class FriendAdapter(
    private val context: Context,
    private val onItemClick: (position: Int, data: FriendEntity) -> Unit,
    private val onDeleteClick: (position: Int, data: FriendEntity) -> Unit
) : RecyclerView.Adapter<FriendAdapter.Companion.FriendViewHolder>() {
    private var listItem = emptyList<FriendEntity>()
    companion object {
        class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tv_name)
            val tvSchool: TextView = view.findViewById(R.id.tv_school)
            val ivDelete: ImageView = view.findViewById(R.id.iv_delete)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false)
        )
    }
    override fun getItemCount(): Int {
        return listItem.size
    }
    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentItem = listItem[position]

        holder.tvName.text = currentItem.name
        holder.tvSchool.text = currentItem.school
//        holder.ivPhoto.setImageDrawable(currentItem.photo)

        holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
        holder.ivDelete.setOnClickListener { onDeleteClick(position, currentItem) }
    }
    fun setData(list: List<FriendEntity>) {
        this.listItem = list
        notifyDataSetChanged()
    }
}