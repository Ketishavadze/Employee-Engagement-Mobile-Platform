package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemEventCardVerticalBinding
import com.example.crosscollab.domain.model.UpcomingEvent

class UpcomingEventsAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<UpcomingEvent, UpcomingEventsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemEventCardVerticalBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemEventCardVerticalBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: UpcomingEvent) {
            b.tvDay.text = item.day
            b.tvMonth.text = item.month
            b.tvEventTitle.text = item.title
            b.tvTime.text = item.time
            b.tvLocation.text = item.location
            b.root.setOnClickListener { onClick(item.id) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<UpcomingEvent>() {
            override fun areItemsTheSame(a: UpcomingEvent, b: UpcomingEvent) = a.id == b.id
            override fun areContentsTheSame(a: UpcomingEvent, b: UpcomingEvent) = a == b
        }
    }
}
