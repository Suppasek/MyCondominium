package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suppasek.mycondo.model.Announce
import com.suppasek.mycondo.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel : ViewModel() {

    private val announces = MutableLiveData<ArrayList<Announce>>()
    private val exception: MutableLiveData<String> = MutableLiveData()
    private val dataRepository: DataRepository = DataRepository()
    private val disposeBag = CompositeDisposable()


    fun getAnnounceData() {
        disposeBag.add(
                dataRepository.getAnnounceData()
                        .subscribe({ announces ->
                            //get data success
                            this.announces.postValue(ArrayList(announces))
                            disposeBag.clear()
                        }, { exception ->
                            //got exception
                            this.exception.postValue(exception.message)
                            disposeBag.clear()
                        }))
    }

    fun observeAnnounces(): LiveData<ArrayList<Announce>> {
        return announces
    }

    fun observeException(): LiveData<String> {
        return exception
    }

}