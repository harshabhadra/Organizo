package com.harshabhadra.organizo.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harshabhadra.organizo.database.Category
import com.harshabhadra.organizo.databinding.CategoryItemHomeBinding
import kotlin.random.Random


class HomeCategoryAdapter :
    ListAdapter<Category, HomeCategoryAdapter.CategoryViewHolder>(CategoryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        val random = Random
        val red = random.nextInt(255 -0+1)+0
        val green = random.nextInt(255 -0+1)+0
        val blue = random.nextInt(255 -0+1)+0

        val draw = GradientDrawable()
        draw.setColor(Color.rgb(red, green, blue))
        draw.shape = GradientDrawable.RECTANGLE
        holder.bind(item,draw)
    }

    //ViewHolder class for category items
    class CategoryViewHolder private constructor(val binding: CategoryItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, draw:GradientDrawable) {
            binding.category = category
            binding.categoryCard.background = draw
        }

        companion object {
            fun from(parent: ViewGroup): CategoryViewHolder {
                val binding = CategoryItemHomeBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                return CategoryViewHolder(binding)
            }
        }
    }
}

class CategoryDiffUtilCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}