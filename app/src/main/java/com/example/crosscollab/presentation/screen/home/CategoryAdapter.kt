package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.databinding.ItemCategoryBinding
import com.example.crosscollab.domain.model.Category

class CategoryAdapter(
    private val onClick: (Int) -> Unit
) : ListAdapter<Category, CategoryAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        VH(ItemCategoryBinding.inflate(
            LayoutInflater.from(p.context), p, false
        ))

    override fun onBindViewHolder(h: VH, p: Int) = h.bind(getItem(p))

    inner class VH(private val b: ItemCategoryBinding) :
        RecyclerView.ViewHolder(b.root) {

        fun bind(item: Category) {
            b.tvCategoryName.text = item.name
            b.tvEventCount.text = "${item.count} events"
            // map icon string → drawable if you want
            b.root.setOnClickListener { onClick(item.id) }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(a: Category, b: Category) = a.id == b.id
            override fun areContentsTheSame(a: Category, b: Category) = a == b
        }
    }
}
