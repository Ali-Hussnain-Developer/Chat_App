package com.example.chatapp.fragmentUtils


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


object FragmentUtils {

    fun changeFragment(layout: Int, fragment: Fragment, fragmentManager: FragmentManager) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}

