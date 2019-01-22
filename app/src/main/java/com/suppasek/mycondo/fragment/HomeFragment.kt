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
import androidx.recyclerview.widget.RecyclerView
import com.suppasek.mycondo.adapter.AnnounceAdapter
import com.suppasek.mycondo.R
import com.suppasek.mycondo.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeFragment : Fragment() {

    private var announceAdapter = AnnounceAdapter()
    private lateinit var model: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        setToolbarName()
        setRecyclerView()
        observeAnnounces()
        observeException()
        model.getAnnounceData()
    }

    private fun observeAnnounces() {
        model.observeAnnounces()
                .observe(this, Observer { announces ->
                    setProgressBar(false)

                    if (announces.size > 0) {
                        announceAdapter.setItemList(announces)
                        announceAdapter.notifyDataSetChanged()
                    }
                    else if (announces.size == 0) {
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
        home_nothing.visibility = View.VISIBLE
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

    private fun setRecyclerView() {
        announceAdapter = AnnounceAdapter()
        home_announce_list.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        home_announce_list.adapter = announceAdapter
    }

    private fun setProgressBar(switch: Boolean) {
        if (switch && home_progress_bar != null) {
            home_progress_bar.visibility = View.VISIBLE
        } else if (home_progress_bar != null) {
            home_progress_bar.visibility = View.INVISIBLE
        }
    }

    private fun setToolbarName() {
        activity!!.toolbar_name.visibility = View.VISIBLE
        activity!!.toolbar_name.text = "ข่าวสาร"
    }
    
}