package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suppasek.mycondo.model.Package
import com.suppasek.mycondo.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class PackageViewModel : ViewModel() {

    private lateinit var room: String
    private var packagesLiveData = MutableLiveData<ArrayList<Package>>()
    private val exception: MutableLiveData<String> = MutableLiveData()
    private val dataRepository: DataRepository = DataRepository()
    private val disposeBag = CompositeDisposable()

    fun getPackageData() {
        disposeBag.add(
                dataRepository.getPackageData(room)
                        .subscribe({ packages ->
                            //get data success
                            this.packagesLiveData.postValue(packages)
                        }, { exception ->
                            //got exception
                            this.exception.postValue(exception.message)
                        }))
    }

    fun updatePackageData(packageNo: String) {
        disposeBag.add(
                dataRepository.updatePackageData(packageNo, room)
                        .subscribe({
                            //update complete
                            disposeBag.clear()
                        }, { exception ->
                            this.exception.postValue(exception.message)
                            disposeBag.clear()
                        })
        )
    }

    fun observePackageData(): LiveData<ArrayList<Package>> {
        return packagesLiveData
    }

    fun observeException(): LiveData<String> {
        return exception
    }

    fun setRoomNo(room: String) {
        this.room = room
    }
}