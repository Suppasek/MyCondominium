package com.suppasek.mycondo.activity

import android.content.Context
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.fragment.HomeFragment
import com.suppasek.mycondo.fragment.LoginFragment
import com.suppasek.mycondo.fragment.PackageFragment
import com.suppasek.mycondo.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    private var auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    var user : FirebaseUser? = auth.currentUser
    var room : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authen()
        setNavMenu()
    }

    private fun setNavMenu() {
        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_menu)
        navigationAdapter.setupWithBottomNavigation(findViewById(R.id.bottom_menu))

        //set home menu by default
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

    //check if user has ever logged in
    fun authen() {
        if (user == null) {
            bottom_menu.visibility = View.INVISIBLE
            switchFragment(LoginFragment())
        }
        else {
            bottom_menu.visibility = View.VISIBLE
            switchFragment(homeFragment)
            getRoomFromPreference()
        }
    }

    private fun getRoomFromPreference() {
        val sp = getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        room = sp.getString("room", "0")

        //after that get amount of package for notification
        getPackageData()
    }

    //get amount of package
    fun getPackageData() {
        firestore.collection("rooms")
                .document("house_no $room")
                .collection("package")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener {documents ->
                    setPackageNoti(documents.size())
                }
    }

    private fun setPackageNoti(size : Int) {
        if (size > 0) {
            bottom_menu.setNotification(size.toString(), 2)
        }
        else {
            bottom_menu.setNotification("", 2)
        }
    }
}
