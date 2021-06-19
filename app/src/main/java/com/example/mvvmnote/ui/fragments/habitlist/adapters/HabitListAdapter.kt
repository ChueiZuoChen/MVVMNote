package com.example.mvvmnote.ui.fragments.habitlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnote.R
import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.databinding.RecyclerHabitItemBinding
import com.example.mvvmnote.ui.fragments.habitlist.HabitListFragmentDirections
import com.example.mvvmnote.ui.viewmodels.HabitViewModel
import com.example.mvvmnote.utils.Calculations

class HabitListAdapter(private val clickListener: (Habit) -> Unit) :
    RecyclerView.Adapter<HabitListAdapter.MyViewHolder>() {
    var habitList = emptyList<Habit>()

    inner class MyViewHolder(private var itemBinding: RecyclerHabitItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(habit: Habit,clickListener: (Habit) -> Unit){
            itemBinding.ivHabitIcon.setImageResource(habit.imageId)
            itemBinding.tvItemDescription.text = habit.habit_description
            itemBinding.tvTimeElapsed.text = Calculations.calculateTimeBetweenDates(habit.habit_startTime)
            itemBinding.tvItemCreatedTimeStamp.text = "Since: ${habit.habit_startTime}"
            itemBinding.tvItemTitle.text=habit.habit_title
            itemBinding.cvCardView.setOnClickListener { clickListener(habit) }
        }
    }

    fun setData(habits: List<Habit>) {
        habitList = habits
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitListAdapter.MyViewHolder {
        val itemBinding =
            RecyclerHabitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HabitListAdapter.MyViewHolder, position: Int) {
        holder.bind(habitList[position],clickListener)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }
}