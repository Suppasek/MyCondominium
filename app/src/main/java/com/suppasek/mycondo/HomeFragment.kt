package com.suppasek.mycondo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val firestore = FirebaseFirestore.getInstance()
    private var announces = ArrayList<Announce>()
    private var announceAdapter = AnnounceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setProgressBar(true)
        getData()
        setRecyclerView()
    }

    private fun getData() {
        firestore.collection("announce").orderBy("recordNo").get().addOnSuccessListener {documents ->
            for (document in documents) {
                announces.add(document.toObject(Announce::class.java))
                setProgressBar(false)
                announceAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setRecyclerView() {
        home_announcce_list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        announceAdapter.setItemList(announces)
        home_announcce_list.adapter = announceAdapter
    }

    private fun setProgressBar(switch : Boolean) {
        if (switch) home_progress_bar.visibility = View.VISIBLE
        else home_progress_bar.visibility = View.INVISIBLE
    }
}