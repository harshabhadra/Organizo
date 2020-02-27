package com.harshabhadra.organizo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harshabhadra.organizo.database.Category
import com.harshabhadra.organizo.databinding.CategoryItemHomeBinding

class HomeCategoryAdapter : ListAdapter<Category,HomeCategoryAdapter.CategoryViewHolder>(CategoryDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        return CategoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }

    class CategoryViewHolder private constructor(val binding:CategoryItemHomeBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(category:Category){
            binding.category = category
        }

        companion object{

            fun from(parent:ViewGroup): CategoryViewHolder{

                val binding = CategoryItemHomeBinding
                    .inflate(LayoutInflater.from(parent.context),parent,false)
                return CategoryViewHolder(binding)
            }
        }
    }
}
class CategoryDiffUtilCallback : DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {

        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }

}