package com.example.mvvmnote.ui.fragments.habitlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mvvmnote.R
import com.example.mvvmnote.databinding.FragmentHabitListBinding

class HabitListFragment : Fragment(R.layout.fragment_habit_list) {
    private lateinit var binding :FragmentHabitListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_habitListFragment_to_createHabitItemFragment)
        }
    }
}