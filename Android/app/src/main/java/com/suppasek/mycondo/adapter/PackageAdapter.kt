package com.suppasek.mycondo.adapter

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.suppasek.mycondo.R
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.model.Package
import kotlinx.android.synthetic.main.item_package.view.*
import com.suppasek.mycondo.fragment.PackageFragment
import com.suppasek.mycondo.viewmodel.PackageViewModel


class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    lateinit var room : String
    var packages = ArrayList<Package>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)

        //set click listener for each item
        v.setOnClickListener {
            removeItem(position, v)
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = packages[position]

        viewHolder.name.text = item.name
        viewHolder.key.text = item.packageNo
        viewHolder.verify.text = item.verify

    }

    override fun getItemCount(): Int {
        return packages.size
    }

    fun setItemList(packages: ArrayList<Package>, room: String) {
        this.packages = packages
        this.room = room
    }

    private fun removeItem(position: Int, v: View) {
        //create dialog
        val builder: AlertDialog.Builder? = (v.context).let { AlertDialog.Builder(it) }
        builder?.setMessage("รับพัสดุเรียบร้อยแล้วใช่หรือไม่")

        //in case user has accept their package
        builder?.setPositiveButton("ยืนยัน") { dialog, _ ->
            updateData(position, v)
            dialog.dismiss()
        }

        builder?.setNegativeButton("ยกเลิก") {
            dialog, _ ->
            dialog.dismiss()
        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun updateData(position: Int, v: View) {
        val model = PackageViewModel()

        //update package status
        model.setRoomNo(room)
        model.updatePackageData(packages[position].packageNo)

        packages.remove(packages[position])

        //reset bottom menu notification
        (v.context as MainActivity).setPackageNoti(packages.size)

        //reload package fragment
        notifyDataSetChanged()
//        (v.context as MainActivity)
//                .supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.main_view, PackageFragment())
//                .commit()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.item_package_name
        val key: TextView = itemView.item_package_number
        val verify: TextView = itemView.item_package_key
    }
}