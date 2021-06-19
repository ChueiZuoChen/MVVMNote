package com.example.mvvmnote.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmnote.data.models.Habit

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("Select * from habit_table ORDER BY id DESC")
    fun getAllHabit():LiveData<List<Habit>>

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()

}