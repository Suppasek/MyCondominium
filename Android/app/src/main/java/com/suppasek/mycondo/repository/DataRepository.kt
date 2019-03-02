package com.suppasek.mycondo.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.suppasek.mycondo.model.Announce
import com.suppasek.mycondo.model.Package
import com.suppasek.mycondo.model.WaterRecord
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getRoomNumber(uid: String): Single<String> {
        return Single.create { emitter: SingleEmitter<String> ->
            firestore.collection("users")
                    .document(uid)
                    .get()
                    .addOnSuccessListener { task ->
                        emitter.onSuccess(task.get("room").toString())
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAnnounceData(): Single<ArrayList<Announce>> {
        val announces = ArrayList<Announce>()
        return Single.create { emitter: SingleEmitter<ArrayList<Announce>> ->
            firestore.collection("announce")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            announces.add(document.toObject(Announce::class.java))
                        }
                        emitter.onSuccess(announces)
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getPackageData(room: String): Observable<ArrayList<Package>> {
        val packages = ArrayList<Package>()
        return Observable.create { emitter: ObservableEmitter<ArrayList<Package>> ->
            firestore.collection("rooms")
                    .document("house_no $room")
                    .collection("package")
                    .addSnapshotListener { querySnapshot, exception ->
                        packages.clear()
                        querySnapshot?.documents?.forEach { document ->
                            //cet only document with pending status
                            if (document.get("status") == "pending") {
                                packages.add(document.toObject(Package::class.java)!!)
                            }
                        }
                        if (exception != null) {
                            emitter.onError(exception)
                        }
                        emitter.onNext(packages)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getWaterRecordData(room: String, year: String): Single<ArrayList<WaterRecord>> {
        val waterRecords = ArrayList<WaterRecord>()
        return Single.create { emitter: SingleEmitter<ArrayList<WaterRecord>> ->
            firestore.collection("rooms")
                    .document("house_no $room")
                    .collection("water_usage")
                    .whereEqualTo("year", year)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            waterRecords.add(document.toObject(WaterRecord::class.java))
                        }
                        emitter.onSuccess(waterRecords)
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun updatePackageData(packageNo: String, room: String): Completable {
        return Completable.create { emitter: CompletableEmitter ->
            firestore.collection("rooms")
                    .document("house_no $room")
                    .collection("package")
                    .document(packageNo)
                    .update("status", "complete")
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}