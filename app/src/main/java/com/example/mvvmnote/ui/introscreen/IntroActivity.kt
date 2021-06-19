package com.example.mvvmnote.ui.introscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvmnote.MainActivity
import com.example.mvvmnote.R
import com.example.mvvmnote.data.models.IntroView
import com.example.mvvmnote.databinding.ActivityIntroBinding
import com.example.mvvmnote.ui.introscreen.adapter.ViewPagerAdapter

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var introView: List<IntroView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addToIntroView()

        binding.viewPager2.adapter = ViewPagerAdapter(introView)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.circleIndicator.setViewPager(binding.viewPager2)

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position ==2){
                    animationButton()
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

    }

    private fun animationButton() {
        binding.btnStartApp.visibility = View.VISIBLE

        binding.btnStartApp.animate().apply {
            duration = 1400
            alpha(1f)
            binding.btnStartApp.setOnClickListener {
                val i = Intent(applicationContext,MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }.start()
    }

    private fun addToIntroView() {
        introView = listOf(
            IntroView("Welcome to Habit!",R.drawable.ic_baseline_emoji_food_beverage_24),
            IntroView("This is second page",R.drawable.ic_outline_smoke_free_24),
            IntroView("This is final page",R.drawable.ic_outline_fastfood_24)
        )
    }


}