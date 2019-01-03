package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.suppasek.mycondo.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel : ViewModel() {

    private var user = FirebaseAuth.getInstance().currentUser
    private lateinit var room : String
    private val packageAmount: MutableLiveData<Int> = MutableLiveData()
    private val exception: MutableLiveData<String> = MutableLiveData()
    private val dataRepository: DataRepository = DataRepository()
    private val disposeBag = CompositeDisposable()

    fun getPackageData() {
        disposeBag.add(
                dataRepository.getPackageData(room)
                        .subscribe({ packages ->
                            //get data success
                            packageAmount.postValue(packages.size)
                        }, { exception ->
                            //got exception
                            this.exception.postValue(exception.message)
                        }))
    }

    //get amount of arrive package
    fun observePackageData(): LiveData<Int> {
        return packageAmount
    }

    fun setRoomNo(room: String) {
        this.room = room
    }

    fun isUserSignedIn(): Boolean {
        return user != null
    }

}