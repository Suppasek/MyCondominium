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
import androidx.appcompat.app.AlertDialog
import com.suppasek.mycondo.fragment.HomeFragment
import com.suppasek.mycondo.fragment.LoginFragment
import com.suppasek.mycondo.fragment.PackageFragment
import com.suppasek.mycondo.R
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.suppasek.mycondo.fragment.WaterFragment
import com.suppasek.mycondo.viewmodel.LoginViewModel
import com.suppasek.mycondo.viewmodel.PackageViewModel
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private var homeFragment = HomeFragment()
    lateinit var room: String
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var packageViewModel: PackageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel()::class.java)
        packageViewModel = ViewModelProviders.of(this).get(PackageViewModel()::class.java)

        if (loginViewModel.isUserSignedIn()) {
            setInitialLayout()
        } else {
            bottom_menu.visibility = View.GONE
            main_toolbar.visibility = View.GONE
            switchFragment(LoginFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        if (loginViewModel.isUserSignedIn()) {
            observeArrivePackage()
            packageViewModel.getPackageData()
        }
    }

    fun setInitialLayout() {
        bottom_menu.visibility = View.VISIBLE
        main_toolbar.visibility = View.VISIBLE

        //send room number from shared preference to view model
        getRoomNoFromSharedPreference()
        packageViewModel.setRoomNo(room)

        switchFragment(homeFragment)
        setNavMenu()
        setLogoutDialog()
    }

    //observe data from view model and set package notification
    private fun observeArrivePackage() {
        packageViewModel.observePackageData()
                .observe(this, Observer { packages ->
                    setPackageNoti(packages.size)
                })
    }

    private fun setNavMenu() {
        val navigationAdapter = AHBottomNavigationAdapter(this, R.menu.bottom_menu)
        navigationAdapter.setupWithBottomNavigation(findViewById(R.id.bottom_menu))

        //set current position to home by default
        bottom_menu.currentItem = 1

        bottom_menu.defaultBackgroundColor = getColor(R.color.bottomBarColor)

        bottom_menu.setOnTabSelectedListener { position, _ ->
            when (position) {
                0 -> {
                    switchFragment(WaterFragment())
                    bottom_menu.refresh()
                    toolbar_name.visibility = View.INVISIBLE
                    water_spinner_year.visibility = View.VISIBLE
                    return@setOnTabSelectedListener true
                }
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

    private fun getRoomNoFromSharedPreference() {
        val sp = getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        room = sp.getString("room", "0")
    }

    fun setPackageNoti(amount: Int) {
        Log.wtf("activity", "set noti $amount")
        //show notification in menu icon if user got new package
        if (amount > 0) {
            bottom_menu.setNotification(amount.toString(), 2)
        }
        else {
            bottom_menu.setNotification("", 2)
        }
    }

    private fun setLogoutDialog() {
        toolbar_logout.setOnClickListener {
            val builder: AlertDialog.Builder? = let { AlertDialog.Builder(it) }
            builder?.setMessage("คุณต้องการออกจากระบบใช่หรือไม่")
            builder?.setPositiveButton("ยืนยัน") { dialog, _ ->
                loginViewModel.signOut()
                switchFragment(LoginFragment())
                bottom_menu.visibility = View.GONE
                main_toolbar.visibility = View.GONE
                dialog.dismiss()
            }
            builder?.setNegativeButton("ยกเลิก") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
        }
    }


}
