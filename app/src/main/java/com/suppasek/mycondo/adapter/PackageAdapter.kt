package com.suppasek.mycondo.adapter

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.suppasek.mycondo.R
import com.suppasek.mycondo.model.Package
import kotlinx.android.synthetic.main.item_package.view.*
import kotlinx.android.synthetic.main.fragment_package.view.*
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.fragment.PackageFragment


class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    var packages = ArrayList<Package>()
    var firestore = FirebaseFirestore.getInstance()
    lateinit var room : String

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)

        //set item click listener
        v.setOnClickListener {
            removeItem(position, v)
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val item = packages[position]

        view.name.text = item.name
        view.key.text = item.packageNo
        view.verify.text = item.verify

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

        builder?.setPositiveButton("ยืนยัน") { dialog, _ ->
            updateData(position, v)
            notifyDataSetChanged()
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
        //update package status
        firestore.collection("rooms")
                .document("house_no $room")
                .collection("package")
                .document(packages[position].packageNo)
                .update("status", "complete")
        packages.remove(packages[position])

        //refresh bottom nav bar notification
        (v.context as MainActivity).getPackageData()

        (v.context as AppCompatActivity)
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_view, PackageFragment())
                .commit()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.item_package_name
        val key: TextView = itemView.item_package_number
        val verify: TextView = itemView.item_package_key
    }
}