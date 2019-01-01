package com.suppasek.mycondo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class MainActivityViewModel : ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()
    private var room : String? = null

    fun setRoomNo(room: String?) {
        this.room = room
    }

    fun isUserSignedIn(): Boolean {
        return user != null
    }

    //get amount of arrive package
    fun notifyPackageData(): LiveData<Int> {
        val packageAmount: MutableLiveData<Int> = MutableLiveData()
        firestore.collection("rooms")
                .document("house_no $room")
                .collection("package")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener { documents ->

                    //in case package aren't arrive shouldn't show notification 'zero'
                    if (documents.size() > 0) {
                        packageAmount.postValue(documents.size())
                    }
                }

        return packageAmount
    }
}