package com.codialstudent.examdecember

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.codialstudent.sqltrash.fragments.EditFragment
import com.codialstudent.sqltrash.fragments.ShowLisFragment

class ViewPagerAdapter (fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int =2

    override fun getItem(position: Int): Fragment {
        return if  (position==0) {
            EditFragment()
        }else{
            ShowLisFragment()
        }
    }

//    override fun getPageTitle(position: Int): CharSequence {
//        return if(position==0){
//            "Edit"
//        }else{
//            "Show"
//        }
//    }
}