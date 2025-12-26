package com.example.crosscollab.presentation.screen.browsebycategory


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemBrowseEventCardBinding
import com.example.crosscollab.domain.model.BrowseEvent
import com.example.crosscollab.domain.model.CategoryEvent


class BrowseEventsAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<CategoryEvent, BrowseEventsAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(
            ItemBrowseEventCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))

    inner class VH(
        private val b: ItemBrowseEventCardBinding
    ) : RecyclerView.ViewHolder(b.root) {

        fun bind(item: BrowseEvent) {
            b.tvEventTitle.text = item.title
            b.tvEventDescription.text = item.description
            b.tvEventDate.text = item.date
            b.tvEventTime.text = item.time
            b.tvEventLocation.text = item.location
            b.tvSpotsBadge.text = item.spotsLeft

            b.tvRegistrationBadge.visibility =
                if (item.isRegistered) View.VISIBLE else View.GONE

            b.btnViewDetails.setOnClickListener {
                onClick(item.id)
            }
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
