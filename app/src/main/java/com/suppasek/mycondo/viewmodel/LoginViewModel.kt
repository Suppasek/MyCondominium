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

class LoginViewModel : ViewModel() {

    private val exception: MutableLiveData<String> = MutableLiveData()
    private val room: MutableLiveData<String> = MutableLiveData()
    private val authenRepository: AuthenRepository = AuthenRepository()
    private val dataRepository: DataRepository = DataRepository()
    private val disposeBag = CompositeDisposable()

    fun authentication(username: String, password: String) {
        disposeBag.add(authenRepository.authentication(username, password)
                ?.subscribe({ result ->
                    //handle authentication result here
                    resultHandle(result)
                }, { error ->
                    //handle exception here
                    exception.postValue(error.message)
                    disposeBag.clear()
                })!!)
    }

    private fun resultHandle(result: Task<AuthResult>) {
        if (result.isSuccessful) {
            getRoomNumber()
        } else {
            //authentication doesn't complete
            exception.postValue(result.exception?.message)
        }
        disposeBag.clear()
    }

    private fun getRoomNumber() {
        disposeBag.add(
                dataRepository.getRoomNumber()
                        .subscribe({ room ->
                            //get data success
                            this.room.postValue(room)
                        }, { exception ->
                            //got exception
                            this.exception.postValue(exception.message)
                        }))
    }

    fun observeException(): LiveData<String> {
        return exception
    }

    fun publishRoomNumber(): LiveData<String> {
        return room
    }

}

