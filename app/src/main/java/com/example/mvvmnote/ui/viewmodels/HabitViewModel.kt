package com.example.mvvmnote.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmnote.data.database.HabitDatabase
import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.logic.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: HabitRepository
    val getAllHabit: LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(application).habitDao()
        repository = HabitRepository(habitDao)
        getAllHabit = repository.getAllHabits
    }

    fun addHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.addHabit(habit)
    }

    fun updateHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateHabit(habit)
    }

    fun deleteHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteHabit(habit)
    }

    fun deleteAllHabit() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllHabits()
    }


}