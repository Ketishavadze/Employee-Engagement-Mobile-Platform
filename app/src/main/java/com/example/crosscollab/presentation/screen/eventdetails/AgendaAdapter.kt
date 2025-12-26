package com.example.crosscollab.presentation.screen.eventdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemAgendaBinding
import com.example.crosscollab.domain.model.AgendaItem

class AgendaAdapter :
    ListAdapter<AgendaItem, AgendaAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(
            ItemAgendaBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemAgendaBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: AgendaItem) {
            b.tvAgendaNumber.text = item.number.toString()
            b.tvAgendaTimeTitle.text = item.timeTitle
            b.tvAgendaDescription.text = item.description
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<AgendaItem>() {
            override fun areItemsTheSame(a: AgendaItem, b: AgendaItem) =
                a.number == b.number
            override fun areContentsTheSame(a: AgendaItem, b: AgendaItem) = a == b
        }
    }
}
