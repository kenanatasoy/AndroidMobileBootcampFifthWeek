package com.example.androidmobilebootcampfifthweek.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun<reified T : AppCompatActivity> Context.startActivity(block : Intent.() -> Unit = {}){
    val intent  = Intent(this , T::class.java)
    startActivity(
        intent.also {
            block.invoke(it)
        }
    )
}

inline fun FragmentManager.startFragment(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Fragment.changeFragmentStatusBarColor(id: Int) {
    activity?.window?.statusBarColor = resources.getColor(id)
}

fun Activity.changeActivityStatusBarColor(id: Int){
    window?.statusBarColor = resources.getColor(id)
}

fun Fragment.showToast(messageToShow : String){
    Toast.makeText(requireContext(), messageToShow, Toast.LENGTH_LONG).show()
}
