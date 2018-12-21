package com.suppasek.mycondo.Activity

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.Fragment.HomeFragment
import com.suppasek.mycondo.Fragment.LoginFragment
import com.suppasek.mycondo.Fragment.PackageFragment
import com.suppasek.mycondo.R
import com.suppasek.mycondo.model.Package
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import android.R.array
import android.graphics.Color
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation


class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    private var auth = FirebaseAuth.getInstance()
    private var user : FirebaseUser? = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authen()
        setNavMenu()
    }

    private fun setNavMenu() {
        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_menu)
        navigationAdapter.setupWithBottomNavigation(findViewById(R.id.bottom_menu))

        bottom_menu.currentItem = 1
        bottom_menu.defaultBackgroundColor = getColor(R.color.colorPrimary)

        bottom_menu.setOnTabSelectedListener { position, _ ->
            when (position) {
                1 -> {
                    switchFragment(homeFragment)
                    return@setOnTabSelectedListener true
                }
                2 -> {
                    switchFragment(PackageFragment())
                    return@setOnTabSelectedListener true
                }
                else -> {
                    return@setOnTabSelectedListener true
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
            getPackageData()
            switchFragment(homeFragment)
        }
    }

    //get amount of package
    private fun getPackageData() {
        firestore.collection("rooms")
                .document("house_no 1")
                .collection("package")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener {documents ->
                    setPackageNoti(documents.size())
                }
    }

    private fun setPackageNoti(size : Int) {
        if (size > 0)
            bottom_menu.setNotification(size.toString(), 2)
    }
}
