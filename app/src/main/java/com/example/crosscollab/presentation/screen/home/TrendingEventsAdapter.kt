package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemEventCardHorizontalBinding
import com.example.crosscollab.domain.model.TrendingEvent

class TrendingEventsAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<TrendingEvent, TrendingEventsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemEventCardHorizontalBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemEventCardHorizontalBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: TrendingEvent) {
            b.tvEventTitle.text = item.title
            b.tvEventDate.text = item.date
            b.tvEventLocation.text = item.location
            b.tvCapacity.text = item.capacity
            b.root.setOnClickListener { onClick(item.id) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<TrendingEvent>() {
            override fun areItemsTheSame(a: TrendingEvent, b: TrendingEvent) = a.id == b.id
            override fun areContentsTheSame(a: TrendingEvent, b: TrendingEvent) = a == b
        }
    }
}
