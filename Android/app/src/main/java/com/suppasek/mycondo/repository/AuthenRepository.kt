package com.suppasek.mycondo.repository

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AuthenRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun authentication(username: String, password: String): Single<String>? {
        return Single.create { emitter: SingleEmitter<String> ->
            auth.signInWithEmailAndPassword(username, password)
                    .addOnSuccessListener{
                        emitter.onSuccess(auth.uid!!)
                    }.addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateDeviceToken(uid: String) {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener { task ->
                    // Get new Instance ID token
                    val token = task.result?.token
                    Log.wtf("token", token)
                    firestore.collection("users")
                            .document(uid)
                            .update("deviceToken", token)
                }
    }

    fun signOut() {
        auth.signOut()
    }

    fun isUserSignedIn(): Boolean {
        return auth.uid != null
    }
}