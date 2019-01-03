package com.suppasek.mycondo.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.model.Announce
import com.suppasek.mycondo.model.Package
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    fun getRoomNumber() : Single<String> {
        return Single.create{emitter: SingleEmitter<String> ->
            firestore.collection("users")
                    .document(currentUser!!.uid)
                    .get()
                    .addOnSuccessListener { task ->
                        emitter.onSuccess(task.get("room").toString())
                    }
                    .addOnFailureListener{exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAnnounceData() : Single<ArrayList<Announce>> {
        val announces = ArrayList<Announce>()
        return Single.create{emitter: SingleEmitter<ArrayList<Announce>> ->
            firestore.collection("announce")
                    .orderBy("recordNo")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            announces.add(document.toObject(Announce::class.java))
                        }
                        emitter.onSuccess(announces)
                    }
                    .addOnFailureListener{exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPackageData(room: String) : Single<ArrayList<Package>> {
        val packages = ArrayList<Package>()
        return Single.create{emitter: SingleEmitter<ArrayList<Package>> ->
            firestore.collection("rooms")
                    .document("house_no $room")
                    .collection("package")
                    .whereEqualTo("status", "pending")
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            packages.add(document.toObject(Package::class.java))
                        }
                        emitter.onSuccess(packages)
                    }
                    .addOnFailureListener{exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}