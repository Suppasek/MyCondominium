package com.suppasek.mycondo.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suppasek.mycondo.R
import com.suppasek.mycondo.model.Package
import kotlinx.android.synthetic.main.item_package.view.*

class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    var packages = ArrayList<Package>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val item = packages[position]

        view.name.text = item.name
        view.key.text = item.packageNo.toString()
        view.verify.text = item.verify
    }

    override fun getItemCount(): Int {
        return packages.size
    }

    fun setItemList(packages: ArrayList<Package>) {
        this.packages = packages
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.item_package_name
        val key = itemView.item_package_number
        val verify = itemView.item_package_key
    }
}