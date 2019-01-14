package com.suppasek.mycondo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.suppasek.mycondo.adapter.PackageAdapter
import com.suppasek.mycondo.R
import com.suppasek.mycondo.activity.MainActivity
import com.suppasek.mycondo.viewmodel.PackageViewModel
import kotlinx.android.synthetic.main.fragment_package.*
import kotlinx.android.synthetic.main.toolbar.*

class PackageFragment : Fragment() {

    private lateinit var model: PackageViewModel
    private var packageAdapter = PackageAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_package, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(this).get(PackageViewModel::class.java)
        model.setRoomNo((activity as MainActivity).room)

        setToolbarName()
        setProgressBar(true)
        setRecyclerView()

        //observe before fetch data
        observePackageData()
        observeException()

        model.getPackageData()

    }

    private fun observePackageData() {
        model.observePackageData()
                .observe(this, Observer { packages ->
                    setProgressBar(false)

                    if (packages.size > 0) {
                        packageAdapter.setItemList(packages, (activity as MainActivity).room)
                        packageAdapter.notifyDataSetChanged()
                    }
                    else {
                        showEmptyExceptionTextView()
                    }
                })

    }

    private fun observeException() {
        model.observeException()
                .observe(this, Observer { exception ->
                    setProgressBar(false)
                    showDialog(exception)
                    showEmptyExceptionTextView()
                })
    }

    private fun showEmptyExceptionTextView() {
        package_nothing.visibility = View.VISIBLE
    }

    private fun setRecyclerView() {
        package_package_list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        package_package_list.adapter = packageAdapter
    }

    private fun setProgressBar(switch: Boolean) {
        if (switch && package_progress_bar != null) {
            package_progress_bar.visibility = View.VISIBLE
        } else if (package_progress_bar != null) {
            package_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun showDialog(message: String) {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setMessage(message)?.setTitle("Error")
        builder?.setPositiveButton("Accept") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    private fun setToolbarName() {
        activity!!.toolbar_name.visibility = View.VISIBLE
        activity!!.toolbar_name.text = "พัสดุ"
    }
}