package com.suppasek.mycondo.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AuthenRepository {

    private val auth = FirebaseAuth.getInstance()

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

    fun signOut() {
        auth.signOut()
    }

    fun isUserSignedIn(): Boolean {
        return auth.uid != null
    }
}