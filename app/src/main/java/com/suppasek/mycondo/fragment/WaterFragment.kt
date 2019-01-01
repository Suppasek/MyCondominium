package com.suppasek.mycondo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suppasek.mycondo.R
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_water.*


class WaterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSpinner()
    }

    private fun setSpinner() {
        val spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, createData())
        water_spinner_year.adapter = spinnerAdapter
    }

    private fun createData() : ArrayList<Int> {
        var yearList = ArrayList<Int>()
        for (i in 2018..2019) {
            yearList.add(i)
        }
        return yearList
    }


}