package com.suppasek.mycondo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suppasek.mycondo.R
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var model : LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setProgressBar(false)
        setConfirmButton()
        observeRoomNumber()
        observeAuthenException()
    }

    private fun setConfirmButton() {
        login_button_login.setOnClickListener {
            val username = login_username.text.toString()
            val password = login_password.text.toString()

            setProgressBar(true)
            model.authentication(username, password)
        }
    }

    private fun observeAuthenException() {
        model.observeException()
                .observe(this, Observer { result ->
                    showDialog(result!!)
                    setProgressBar(false)
                })
    }

    private fun observeRoomNumber() {
        model.observeRoomNumber()
                .observe(this, Observer { room ->
                    //login success then save room number in shared preference
                    savePreference(room!!)
                    model.updateDeviceToken()
                    performMainActivity()
                })
    }

    private fun performMainActivity() {
        (activity as MainActivity).setInitialLayout()
    }

    private fun showDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setMessage(message)?.setTitle("Error")
        builder?.setPositiveButton("Accept") {
            dialog, _ -> dialog.dismiss()
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun savePreference(room: String) {
        val sp = activity!!.getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("room", room)
        editor.apply()
    }

    private fun setProgressBar(switch : Boolean) {
        if(switch) {
            login_box.visibility = View.INVISIBLE
            login_progress.visibility = View.VISIBLE
        }
        else {
            login_progress.visibility = View.INVISIBLE
            login_box.visibility = View.VISIBLE
        }
    }

}