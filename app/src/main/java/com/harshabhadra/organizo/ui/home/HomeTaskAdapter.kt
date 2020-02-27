package com.harshabhadra.organizo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harshabhadra.organizo.database.Task
import com.harshabhadra.organizo.databinding.TaskItemLayoutBinding

class HomeTaskAdapter : ListAdapter<Task,HomeTaskAdapter.HomeTaskViewHolder>(HomeTaskDiffUtilCallBack()){

    class HomeTaskViewHolder private constructor(val binding:TaskItemLayoutBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(item:Task){
            binding.task = item
        }

        companion object{
            fun from(parent:ViewGroup): HomeTaskViewHolder{
                val binding = TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return HomeTaskViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTaskViewHolder {

        return HomeTaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeTaskViewHolder, position: Int) {

        val item = getItem(position)
        holder.bind(item)
    }
}

class HomeTaskDiffUtilCallBack : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}