package com.suppasek.mycondo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.suppasek.mycondo.R
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.adapter.WaterAdapter
import com.suppasek.mycondo.viewmodel.WaterViewModel
import kotlinx.android.synthetic.main.fragment_water.*
import kotlinx.android.synthetic.main.toolbar.*

class WaterFragment : Fragment() {

    private lateinit var model: WaterViewModel
    private var yearList = ArrayList<Int>()
    private val waterAdapter = WaterAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_water, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(this).get(WaterViewModel::class.java)
        model.setRoomNo((activity as MainActivity).room)
        yearList = createDataForSpinner()
        setProgressBar(true)

        setRecyclerView()
        setSpinner()
        observeWaterRecord()
    }

    private fun observeWaterRecord() {
        model.observeWaterRecord()
                .observe(this, Observer { waterRecord ->
                    setProgressBar(false)

                    if (waterRecord.size > 0) {
                        waterAdapter.setItemList(waterRecord)
                        waterAdapter.notifyDataSetChanged()
                    }
                    else if (waterRecord.size == 0) {
                        water_nothing.visibility = View.VISIBLE
                    }
                })
    }

    private fun setSpinner() {
        val spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, createDataForSpinner())
        activity!!.water_spinner_year.adapter = spinnerAdapter

        activity!!.water_spinner_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //clear for refresh data
                waterAdapter.clearItemList()
                waterAdapter.notifyDataSetChanged()
                water_nothing.visibility = View.INVISIBLE
                setProgressBar(true)
                model.getWaterRecordData(yearList[position].toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun setRecyclerView() {
        water_list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        water_list.adapter = waterAdapter
    }

    private fun setProgressBar(switch: Boolean) {
        if (switch && water_progress_bar != null) {
            water_progress_bar.visibility = View.VISIBLE
        } else if (water_progress_bar != null) {
            water_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun createDataForSpinner(): ArrayList<Int> {
        val yearList = ArrayList<Int>()
        yearList.addAll(2018..2019)
        return yearList
    }

    override fun onStop() {
        super.onStop()
        activity!!.water_spinner_year.visibility = View.GONE
    }

}