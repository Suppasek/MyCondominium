package com.suppasek.mycondo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

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
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_view, HomeFragment()).commit()
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

}