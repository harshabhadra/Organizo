package com.harshabhadra.organizo.ui.userTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.database.Category


class CategoryAdapter
    : ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    var onItemClick: ((pos: Int, view: View) -> Unit)? = null
  inner  class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val categoryName: TextView = itemView.findViewById(R.id.category_name)

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (v != null) {
                onItemClick?.invoke(position,v)
            }
        }
      init {
          itemView.setOnClickListener(this)
      }
    }

    //Get Category Item
    fun getCategoryItem(position:Int):Category{
        return currentList[position]
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.categoryName.text = item.categoryName
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {

        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {

        return oldItem == newItem
    }

}