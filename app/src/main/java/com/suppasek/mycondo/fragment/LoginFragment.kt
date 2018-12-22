package com.suppasek.mycondo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.suppasek.mycondo.R
import kotlinx.android.synthetic.main.fragment_login.*
import android.R.attr.mode
import android.R.attr.name
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.activity.MainActivity


class LoginFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setConfirmButton()
    }

    private fun setConfirmButton() {
        login_button_login.setOnClickListener{
            val username = login_username.text.toString()
            val password = login_password.text.toString()
            auth.signInWithEmailAndPassword(username, password).addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    getRoomData()
                }
                else {
                    showDialog(task.exception.toString())
                }
            }
        }
    }

    private fun showDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setMessage(message)?.setTitle("Error")
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun getRoomData() {
        firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .get()
                .addOnCompleteListener { task ->
                    savePreference(task.result?.get("room").toString())

                }
    }

    private fun savePreference(room : String) {
        val sp = activity!!.getSharedPreferences("myCondoPreference", Context.MODE_PRIVATE)
        val editor = sp.edit()

        editor.putString("room", room)
        editor.apply()
        (activity as MainActivity).user = auth.currentUser
        (activity as MainActivity).authen()
    }

}