package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.R

class TrendingEventsAdapter(
    private val onEventClick: (Event) -> Unit
) : ListAdapter<Event, TrendingEventsAdapter.TrendingEventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingEventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card_horizontal, parent, false)
        return TrendingEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingEventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TrendingEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEventImage: ImageView = itemView.findViewById(R.id.ivEventImage)
        private val tvEventTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        private val tvEventDate: TextView = itemView.findViewById(R.id.tvEventDate)
        private val tvEventLocation: TextView = itemView.findViewById(R.id.tvEventLocation)
        private val tvCapacity: TextView = itemView.findViewById(R.id.tvCapacity)

        fun bind(event: Event) {
            tvEventTitle.text = event.title
            tvEventDate.text = event.date
            tvEventLocation.text = event.location

            // Set capacity badge text and visibility
            if (event.capacity != null) {
                tvCapacity.visibility = View.VISIBLE
                tvCapacity.text = event.capacity
            } else {
                tvCapacity.visibility = View.GONE
            }

            // Load image with your preferred image loading library (Glide, Coil, etc.)
            // Glide.with(itemView.context).load(event.imageUrl).into(ivEventImage)
            // Or use Coil:
            // ivEventImage.load(event.imageUrl)

            itemView.setOnClickListener {
                onEventClick(event)
            }
        }
    }

    private class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}