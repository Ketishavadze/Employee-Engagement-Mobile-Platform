package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemFaqBinding
import com.example.crosscollab.domain.model.Faq


class FaqAdapter :
    ListAdapter<Faq, FaqAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemFaqBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemFaqBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: Faq) {
            b.tvQuestion.text = item.question
            b.tvAnswer.text = item.answer

            b.root.setOnClickListener {
                b.tvAnswer.visibility =
                    if (b.tvAnswer.visibility == View.GONE)
                        View.VISIBLE else View.GONE
            }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Faq>() {
            override fun areItemsTheSame(a: Faq, b: Faq) = a.id == b.id
            override fun areContentsTheSame(a: Faq, b: Faq) = a == b
        }
    }
}
