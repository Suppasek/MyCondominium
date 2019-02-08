package com.suppasek.mycondo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suppasek.mycondo.model.WaterRecord
import com.suppasek.mycondo.repository.DataRepository
import io.reactivex.disposables.CompositeDisposable

class WaterViewModel : ViewModel() {

    private lateinit var room: String
    private val waterRecord = MutableLiveData<ArrayList<WaterRecord>>()
    private val exception: MutableLiveData<String> = MutableLiveData()
    private val dataRepository: DataRepository = DataRepository()
    private val disposeBag = CompositeDisposable()

    fun getWaterRecordData(year: String) {
        disposeBag.add(dataRepository.getWaterRecordData(room, year)
                .subscribe({ waterRecord ->
                    //get data success
                    this.waterRecord.postValue(ArrayList(waterRecord))
                    disposeBag.clear()
                }, { exception ->
                    //got exception
                    this.exception.postValue(exception.message)
                    disposeBag.clear()
                }))
    }

    fun observeWaterRecord() : LiveData<ArrayList<WaterRecord>> {
        return waterRecord
    }

    fun observeException() : LiveData<String> {
        return exception
    }

    fun setRoomNo(room: String) {
        this.room = room
    }
}