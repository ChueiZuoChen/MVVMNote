package com.example.mvvmnote.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mvvmnote.R
import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.databinding.FragmentCreateHabitItemBinding
import com.example.mvvmnote.ui.viewmodels.HabitViewModel
import com.example.mvvmnote.utils.Calculations
import java.util.*

class CreateHabitItemFragment : Fragment(R.layout.fragment_create_habit_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModel: HabitViewModel
    private lateinit var binding: FragmentCreateHabitItemBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHabitItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        binding.btnConfirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableOnSelected()

    }

    private fun addHabitToDB() {
        title = binding.etHabitTitle.text.toString()
        description = binding.etHabitDescription.text.toString()
        timeStamp = "$cleanDate $cleanTime"
        if (!(title.isEmpty()|| description.isEmpty() || timeStamp.isEmpty() || drawableSelected ==0)){
            val habit = Habit(0,title,description,timeStamp,drawableSelected)
            habitViewModel.addHabit(habit)
            Toast.makeText(requireContext(), "Habit created successfully!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_createHabitItemFragment_to_habitListFragment)
        }else {
            Toast.makeText(requireContext(),"Please fill all field!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableOnSelected() {
        binding.ivFastFoodSelected.setOnClickListener {
            binding.ivFastFoodSelected.isSelected = !binding.ivFastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_outline_fastfood_24
            binding.ivSmokingSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivSmokingSelected.setOnClickListener {
            binding.ivSmokingSelected.isSelected = !binding.ivSmokingSelected.isSelected
            drawableSelected = R.drawable.ic_outline_smoke_free_24
            binding.ivFastFoodSelected.isSelected = false
            binding.ivTeaSelected.isSelected = false
        }

        binding.ivTeaSelected.setOnClickListener {
            binding.ivTeaSelected.isSelected = !binding.ivTeaSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_emoji_food_beverage_24
            binding.ivFastFoodSelected.isSelected = false
            binding.ivSmokingSelected.isSelected = false
        }
    }

    private fun pickDateAndTime() {
        binding.btnPickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
    }


    override fun onTimeSet(view: TimePicker?, hourX: Int, minuteX: Int) {
        cleanTime = Calculations.cleanTime(hourX, minuteX)
        binding.tvTimeSelected.text = "Time: $cleanTime"
    }

    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelected.text = "Date: $cleanDate"
    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }


}