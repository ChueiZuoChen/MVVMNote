package com.example.mvvmnote.ui.fragments.updatehabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mvvmnote.R
import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.databinding.FragmentUpdateHabitItemBinding
import com.example.mvvmnote.ui.viewmodels.HabitViewModel
import com.example.mvvmnote.utils.Calculations
import java.util.*

class UpdateHabitItemFragment : Fragment(R.layout.fragment_update_habit_item),
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
    private lateinit var binding: FragmentUpdateHabitItemBinding

    //safeArgs
    private val args : UpdateHabitItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateHabitItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        binding.etHabitTitleUpdate.setText(args.selectedHabit.habit_title)
        binding.etHabitDescriptionUpdate.setText(args.selectedHabit.habit_description)


        drawableOnSelected()

        pickDateAndTime()
        binding.btnConfirmUpdate.setOnClickListener {
            updateHabit()
        }

        setHasOptionsMenu(true)
    }

    private fun updateHabit() {
        title = binding.etHabitTitleUpdate.text.toString()
        description = binding.etHabitDescriptionUpdate.text.toString()
        timeStamp = "$cleanDate $cleanTime"

        if (!(title.isEmpty()|| description.isEmpty() || timeStamp.length<16 || drawableSelected ==0)){
            val habit = Habit(args.selectedHabit.id,title,description,timeStamp,drawableSelected)

            habitViewModel.updateHabit(habit)
            Toast.makeText(context,"Habit updated successfully!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateHabitItemFragment_to_habitListFragment)
        }else {
            Toast.makeText(requireContext(),"Please fill all field!",Toast.LENGTH_SHORT).show()
        }
    }


    private fun drawableOnSelected() {
        binding.ivFastFoodSelectedUpdate.setOnClickListener {
            binding.ivFastFoodSelectedUpdate.isSelected = !binding.ivFastFoodSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_outline_fastfood_24
            binding.ivSmokingSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivSmokingSelectedUpdate.setOnClickListener {
            binding.ivSmokingSelectedUpdate.isSelected = !binding.ivSmokingSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_outline_smoke_free_24
            binding.ivFastFoodSelectedUpdate.isSelected = false
            binding.ivTeaSelectedUpdate.isSelected = false
        }

        binding.ivTeaSelectedUpdate.setOnClickListener {
            binding.ivTeaSelectedUpdate.isSelected = !binding.ivTeaSelectedUpdate.isSelected
            drawableSelected = R.drawable.ic_baseline_emoji_food_beverage_24
            binding.ivFastFoodSelectedUpdate.isSelected = false
            binding.ivSmokingSelectedUpdate.isSelected = false
        }
    }




    private fun pickDateAndTime() {
        binding.btnPickDateUpdate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        binding.btnPickTimeUpdate.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
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

    override fun onTimeSet(view: TimePicker?, hourX: Int, minuteX: Int) {
        cleanTime = Calculations.cleanTime(hourX, minuteX)
        binding.tvTimeSelectedUpdate.text = "Time: $cleanTime"
    }

    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        binding.tvDateSelectedUpdate.text = "Date: $cleanDate"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.simgle_item_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_delete -> deleteHabit(args.selectedHabit)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteHabit(selectedHabit: Habit) {
        habitViewModel.deleteHabit(selectedHabit)
        Toast.makeText(context,"Habit successfully deleted!",Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_updateHabitItemFragment_to_habitListFragment)

    }

}