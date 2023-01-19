package com.codialstudent.sqltrash

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.codialstudent.examdecember.ViewPagerAdapter
import com.codialstudent.sqltrash.adapters.PagerAgentViewModel
import com.codialstudent.sqltrash.databinding.ActivityMainBinding

open class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var pagerAgentViewModel: PagerAgentViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val pageAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        pagerAgentViewModel = ViewModelProvider(this).get(PagerAgentViewModel::class.java)
        binding.tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_baseline_edit_24)
        binding.tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_baseline_list_24)
        var title = "New Contact"
        supportActionBar?.title = title


        binding.viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> title = "New Contact"
                    1 -> title = "Contacts"
                }



                supportActionBar?.title = title
            }

            override fun onPageScrollStateChanged(state: Int) {
                val view = binding.view
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        })

    }

    open fun goPage1() {
        binding.viewPager.currentItem = 1
    }

    open fun goPage0() {
        binding.viewPager.currentItem = 0
    }

}