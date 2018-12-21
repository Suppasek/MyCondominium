package com.suppasek.mycondo.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.suppasek.mycondo.Adapter.PackageAdapter
import com.suppasek.mycondo.R
import com.suppasek.mycondo.model.Package
import kotlinx.android.synthetic.main.fragment_package.*

class PackageFragment : Fragment() {

    private val firestore = FirebaseFirestore.getInstance()
    private var packages = ArrayList<Package>()
    private var packageAdapter = PackageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_package, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setProgressBar(true)
        setRecyclerView()
        getData()
    }

    private fun getData() {
        firestore.collection("rooms")
                .document("house_no 1")
                .collection("package")
                .whereEqualTo("status", "pending")
                .get()
                .addOnSuccessListener {documents ->
            for (document in documents) {
                packages.add(document.toObject(Package::class.java))
            }
                    Log.wtf("package", packages.size.toString())
                    setProgressBar(false)
                    packageAdapter.notifyDataSetChanged()
        }.addOnFailureListener{
                    Log.wtf("package", it.cause.toString())
                    setProgressBar(false)
                }
    }

    private fun setRecyclerView() {
        package_package_list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        packageAdapter.setItemList(packages)
        package_package_list.adapter = packageAdapter
    }

    private fun setProgressBar(switch : Boolean) {
        if (switch && package_progress_bar != null){
            package_progress_bar.visibility = View.VISIBLE
        }
        else if (package_progress_bar != null) {
            package_progress_bar.visibility = View.INVISIBLE
        }
    }
}