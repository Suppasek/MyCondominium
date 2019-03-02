package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.repository.AuthenRepository
import com.suppasek.mycondo.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel() : ViewModel() {

    private val exception: MutableLiveData<String> = MutableLiveData()
    private lateinit var uid: String
    private val room: MutableLiveData<String> = MutableLiveData()
    private val disposeBag = CompositeDisposable()
    private var dataRepository: DataRepository = DataRepository()
    private var authenRepository: AuthenRepository = AuthenRepository()

    //for test
    constructor(dataRepository: DataRepository, authenRepository: AuthenRepository) : this() {
        this.dataRepository = dataRepository
        this.authenRepository = authenRepository
    }

    fun authentication(username: String, password: String) {
        disposeBag.add(authenRepository.authentication(username, password)
                ?.subscribe({ result ->
                    //handle authentication result here
                    this.uid = result
                    getRoomNumber(result)
                }, { error ->
                    //handle exception here
                    exception.postValue(error.message)
                })!!)
    }

    fun updateDeviceToken() {
        authenRepository.updateDeviceToken(uid)
    }

    fun getRoomNumber(uid: String) {
        disposeBag.add(
                dataRepository.getRoomNumber(uid)
                        .subscribe({ room ->
                            //get data success
                            this.room.postValue(room)
                        }, { exception ->
                            //got exception
                            this.exception.postValue(exception.message)
                        }))
    }

    fun signOut() {
        authenRepository.signOut()
    }

    fun isUserSignedIn(): Boolean {
        return authenRepository.isUserSignedIn()
    }

    fun observeException(): LiveData<String> {
        return exception
    }

    fun observeRoomNumber(): LiveData<String> {
        return room
    }

}

