package com.example.crosscollab.presentation.screen.browseevents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemBrowseEventBinding
import com.example.crosscollab.domain.model.BrowseEvent

class BrowseEventsAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<BrowseEvent, BrowseEventsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(
            ItemBrowseEventBinding.inflate(
                LayoutInflater.from(p.context),
                p,
                false
            )
        )

    override fun onBindViewHolder(h: VH, p: Int) =
        h.bind(getItem(p))

    inner class VH(
        private val b: ItemBrowseEventBinding
    ) : RecyclerView.ViewHolder(b.root) {

        fun bind(item: BrowseEvent) {
            b.tvMonth.text = item.month
            b.tvDay.text = item.day
            b.tvCategory.text = item.category
            b.tvStatus.text = item.status
            b.tvEventTitle.text = item.title
            b.tvTime.text = item.time
            b.tvLocation.text = item.location
            b.tvRegisteredCount.text =
                "${item.registeredCount} registered"

            if (item.spotsLeft > 0) {
                b.tvSpotsLeft.text = "${item.spotsLeft} spots left"
                b.tvSpotsLeft.visibility = View.VISIBLE
                b.tvWaitlistCount.visibility = View.GONE
            } else {
                b.tvWaitlistCount.text =
                    "${item.waitlistCount} on waitlist"
                b.tvWaitlistCount.visibility = View.VISIBLE
                b.tvSpotsLeft.visibility = View.GONE
            }

            b.root.setOnClickListener { onClick(item.id) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<BrowseEvent>() {
            override fun areItemsTheSame(a: BrowseEvent, b: BrowseEvent) =
                a.id == b.id

            override fun areContentsTheSame(a: BrowseEvent, b: BrowseEvent) =
                a == b
        }
    }
}
