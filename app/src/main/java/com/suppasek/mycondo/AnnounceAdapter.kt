package com.suppasek.mycondo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_announce.view.*

class AnnounceAdapter : RecyclerView.Adapter<AnnounceAdapter.ViewHolder>() {

    private var announces = ArrayList<Announce>()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_announce, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: Announce = announces[position]

        viewHolder.tag.text = item.tag
        viewHolder.body.text = item.body
    }

    override fun getItemCount(): Int {
        return announces.size
    }

    fun setItemList(announces: ArrayList<Announce>) {
        this.announces = announces
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tag = itemView.item_announce_tag
        val body = itemView.item_announce_body
    }
}