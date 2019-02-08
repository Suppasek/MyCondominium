package com.suppasek.mycondo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.suppasek.mycondo.R
import com.suppasek.mycondo.model.WaterRecord
import kotlinx.android.synthetic.main.item_water.view.*
import java.util.*


class WaterAdapter : RecyclerView.Adapter<WaterAdapter.ViewHolder>() {

    private var waterRecord = ArrayList<WaterRecord>()
    private var mExpandedPosition = -1
    private var isFirstTap = true
    private var month = ArrayList<String>()

    init {
        setMonth()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_water, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = waterRecord[position]

        //map month (show january instead of 1)
        viewHolder.month.text = month[Integer.parseInt(item.month) - 1]
        viewHolder.meter.text = item.recordUnit.toString()
        viewHolder.totalUnit.text = item.totalUnit.toString()
        viewHolder.price.text = item.price.toString()

        //item expand stuff
        val isExpanded = position == mExpandedPosition || (position == 0 && isFirstTap)
        viewHolder.body.visibility = if (isExpanded) View.VISIBLE else View.GONE
        viewHolder.itemView.isActivated = isExpanded
        viewHolder.itemView.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            //TransitionManager.beginDelayedTransition(recyclerView)
            notifyItemChanged(position)
            isFirstTap = false
        }
    }

    override fun getItemCount(): Int {
        return waterRecord.size
    }

    fun setItemList(waterRecord: ArrayList<WaterRecord>) {
        this.waterRecord = waterRecord
        sortItemList()
    }

    private fun sortItemList() {
        //sort data
        waterRecord.sortWith(Comparator { lhs, rhs ->
            val month1 = Integer.parseInt(lhs.month)
            val month2 = Integer.parseInt(rhs.month)
            month2.compareTo(month1)
        })
    }

    fun clearItemList() {
        waterRecord.clear()
    }

    private fun setMonth() {
        month.add("มกราคม")
        month.add("กุมภาพันธ์")
        month.add("มีนาคม")
        month.add("เมษายน")
        month.add("พฤษภาคม")
        month.add("มิถุนายน")
        month.add("กรกฏาคม")
        month.add("สิงหาคม")
        month.add("กันยายน")
        month.add("ตุลาคม")
        month.add("พฤศจิกายน")
        month.add("ธันวาคม")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val month: TextView = itemView.water_bill_month
        val price: TextView = itemView.water_bill_price
        val totalUnit: TextView = itemView.water_bill_total_unit
        val meter: TextView = itemView.water_bill_meter
        val body: ConstraintLayout = itemView.water_bill_body
    }
}