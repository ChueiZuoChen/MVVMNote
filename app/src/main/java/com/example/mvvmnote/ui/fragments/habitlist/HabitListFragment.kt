package com.example.mvvmnote.ui.fragments.habitlist


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmnote.R
import com.example.mvvmnote.data.models.Habit
import com.example.mvvmnote.databinding.FragmentHabitListBinding
import com.example.mvvmnote.ui.fragments.habitlist.adapters.HabitListAdapter
import com.example.mvvmnote.ui.viewmodels.HabitViewModel

class HabitListFragment : Fragment(R.layout.fragment_habit_list) {
    lateinit var habitList: List<Habit>
    lateinit var habitViewModel: HabitViewModel
    lateinit var adapter: HabitListAdapter
    private lateinit var binding: FragmentHabitListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = HabitListAdapter {
            itemClicked(it)
        }
        binding.rvHabits.adapter = adapter
            binding.rvHabits.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabits.setHasFixedSize(true)
        habitViewModel = ViewModelProvider(requireActivity()).get(HabitViewModel::class.java)

        /*test create crash*/
        //habitViewModel.deleteAllHabit()

        habitViewModel.getAllHabit.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            habitList = it
            if (it.isEmpty()) {
                binding.rvHabits.visibility = View.GONE
                binding.tvEmptyView.visibility = View.VISIBLE
            } else {
                binding.rvHabits.visibility = View.VISIBLE
                binding.tvEmptyView.visibility = View.GONE
            }
        })

        setHasOptionsMenu(true)


        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_habitListFragment_to_createHabitItemFragment)
        }
    }

    private fun itemClicked(habit: Habit) {
        val action =
            HabitListFragmentDirections.actionHabitListFragmentToUpdateHabitItemFragment(habit)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_delete -> habitViewModel.deleteAllHabit()
        }
        return super.onOptionsItemSelected(item)
    }
}