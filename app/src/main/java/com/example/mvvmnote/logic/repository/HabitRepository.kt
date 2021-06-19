package com.example.mvvmnote.logic.repository

import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.logic.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    val getAllHabits = habitDao.getAllHabit()

    suspend fun addHabit(habit: Habit){
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit: Habit){
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit){
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabits(){
        habitDao.deleteAll()
    }


}