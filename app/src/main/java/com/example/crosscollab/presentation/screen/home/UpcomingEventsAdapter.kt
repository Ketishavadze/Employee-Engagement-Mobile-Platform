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

class UpcomingEventsAdapter(
    private val onEventClick: (Event) -> Unit
) : ListAdapter<Event, UpcomingEventsAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event_card_vertical, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        private val tvMonth: TextView = itemView.findViewById(R.id.tvMonth)
        private val tvEventTitle: TextView = itemView.findViewById(R.id.tvEventTitle)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val tvViewDetails: TextView = itemView.findViewById(R.id.tvViewDetails)

        fun bind(event: Event) {
            tvEventTitle.text = event.title

            // Parse and set date components
            // Assuming event.date format is "Jan 15, 2025 • 10:00 AM"
            val dateParts = event.date.split("•")
            val dateStr = dateParts.getOrNull(0)?.trim() ?: event.date
            val timeStr = dateParts.getOrNull(1)?.trim() ?: ""

            // Extract day and month from date string (e.g., "Jan 15, 2025")
            val dateComponents = dateStr.split(" ")
            if (dateComponents.size >= 2) {
                val day = dateComponents[1].replace(",", "")
                val month = dateComponents[0]
                tvDay.text = day
                tvMonth.text = month
            }

            // Set time
            tvTime.text = timeStr

            // Set location
            tvLocation.text = event.location

            itemView.setOnClickListener {
                onEventClick(event)
            }

            tvViewDetails.setOnClickListener {
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