package com.suppasek.mycondo.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.suppasek.mycondo.fragment.HomeFragment
import com.suppasek.mycondo.fragment.LoginFragment
import com.suppasek.mycondo.fragment.PackageFragment
import com.suppasek.mycondo.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.suppasek.mycondo.fragment.WaterFragment
import com.suppasek.mycondo.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    lateinit var room: String
    lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this).get(MainActivityViewModel()::class.java)

        if (model.isUserSignedIn()) {
            setInitialLayout()
        } else {
            bottom_menu.visibility = View.INVISIBLE
            switchFragment(LoginFragment())
        }
    }

    fun setInitialLayout() {
        bottom_menu.visibility = View.VISIBLE

        //send room number from shared preference to view model
        getRoomNoFromSharedPreference()
        model.setRoomNo(room)

        switchFragment(homeFragment)
        setNavMenu()

        observeArrivePackage()
        model.getPackageData()
    }

    //observe data from view model and set package notification
    private fun observeArrivePackage() {
        model.observePackageData()
                .observe(this, Observer { packageAmount ->
                    setPackageNoti(packageAmount!!)
                })
    }

    private fun setNavMenu() {
        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_menu)
        navigationAdapter.setupWithBottomNavigation(findViewById(R.id.bottom_menu))

        //set current position to home by default
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
                0 -> {
                    switchFragment(WaterFragment())
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

    private fun getRoomNoFromSharedPreference() {
        val sp = getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        room = sp.getString("room", "0")
    }

    private fun setPackageNoti(amount: Int) {
        //show notification in menu icon if user got new package
        if (amount > 0) {
            bottom_menu.setNotification(amount.toString(), 2)
        }
    }
}
