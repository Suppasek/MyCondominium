package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val exception : MutableLiveData<String> = MutableLiveData()
    private val room : MutableLiveData<String> = MutableLiveData()

    fun authen(username: String, password: String) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                getRoomNumber()
            } else {
                exception.postValue(task.exception.toString())
            }
        }
    }

    fun publishException() : LiveData<String> {
        return exception
    }

    fun publishRoom() : LiveData<String> {
        return room
    }

    private fun getRoomNumber() : LiveData<String> {
        firestore.collection("users")
                .document(auth.currentUser!!.uid)
                .get()
                .addOnCompleteListener { task ->
                    room.postValue(task.result?.get("room").toString())
                }

        return room
    }

}