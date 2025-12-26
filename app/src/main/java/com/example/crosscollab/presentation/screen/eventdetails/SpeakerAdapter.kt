package com.example.crosscollab.presentation.screen.eventdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemSpeakerBinding
import com.example.crosscollab.domain.model.Speaker

class SpeakerAdapter :
    ListAdapter<Speaker, SpeakerAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(
            ItemSpeakerBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemSpeakerBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: Speaker) {
            b.tvSpeakerName.text = item.name
            b.tvSpeakerTitle.text = item.title
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Speaker>() {
            override fun areItemsTheSame(a: Speaker, b: Speaker) = a.id == b.id
            override fun areContentsTheSame(a: Speaker, b: Speaker) = a == b
        }
    }
}
