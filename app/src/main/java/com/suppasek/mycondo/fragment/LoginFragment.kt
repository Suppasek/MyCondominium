package com.suppasek.mycondo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.suppasek.mycondo.R
import kotlinx.android.synthetic.main.fragment_login.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private lateinit var model : LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        setConfirmButton()
        observeRoomNumber()
        observeAuthenException()
    }

    private fun setConfirmButton() {
        login_button_login.setOnClickListener {
            val username = login_username.text.toString()
            val password = login_password.text.toString()

            model.authen(username, password)
        }
    }

    private fun observeAuthenException() {
        model.publishException()
                .observe(this, Observer { result ->
                    showDialog(result!!)
                })
    }

    private fun observeRoomNumber() {
        model.publishRoom()
                .observe(this, Observer { room ->
                    savePreference(room!!)
                    performMainActivity()
                })
    }

    private fun performMainActivity() {
        (activity as MainActivity).setInitialLayout()
    }

    private fun showDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setMessage(message)?.setTitle("Error")
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun savePreference(room: String) {
        val sp = activity!!.getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("room", room)
        editor.apply()
    }

}