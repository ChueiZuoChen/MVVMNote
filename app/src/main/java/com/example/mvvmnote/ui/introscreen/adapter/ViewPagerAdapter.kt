package com.example.mvvmnote.ui.introscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnote.data.models.IntroView
import com.example.mvvmnote.databinding.IntroItemPageBinding

class ViewPagerAdapter(introViews: List<IntroView>) :
    RecyclerView.Adapter<ViewPagerAdapter.IntroViewHolder>() {

    private val list = introViews

    class IntroViewHolder(private var binding: IntroItemPageBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(introView: IntroView){
            binding.tvDescriptionIntro.text = introView.description
            binding.ivImageIntro.setImageResource(introView.imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroViewHolder {
        val introView = IntroItemPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IntroViewHolder(introView)
    }

    override fun onBindViewHolder(holder: IntroViewHolder, position: Int) {
        val currentView = list[position]
        holder.bind(currentView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}