package com.suppasek.mycondo

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    private var auth = FirebaseAuth.getInstance()
    private var user : FirebaseUser? = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authen()
        setNavMenu()
    }

    private fun setNavMenu() {
        bottom_menu.selectedItemId = R.id.menu_home
         bottom_menu.setOnNavigationItemSelectedListener {
             when (it.itemId) {
                 R.id.menu_home -> {
                     switchFragment(homeFragment)
                     return@setOnNavigationItemSelectedListener true
                 }

                 else -> {
                     return@setOnNavigationItemSelectedListener true
                 }
             }
         }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_view, fragment).commit()
    }

    private fun authen() {
        if (user == null) {
            bottom_menu.visibility = View.INVISIBLE
            switchFragment(LoginFragment())
        }
        else {
            bottom_menu.visibility = View.VISIBLE
            switchFragment(homeFragment)
        }
    }
}
