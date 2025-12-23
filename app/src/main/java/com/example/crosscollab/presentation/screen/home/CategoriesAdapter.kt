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

class CategoriesAdapter(
    private val onCategoryClick: (Category) -> Unit
) : ListAdapter<Category, CategoriesAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCategoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        private val tvEventCount: TextView = itemView.findViewById(R.id.tvEventCount)

        fun bind(category: Category) {
            tvCategoryName.text = category.name
            tvEventCount.text = "${category.eventCount} events"

            // Set the category icon drawable
            val iconRes = getCategoryIconResource(category.iconName)
            ivCategoryIcon.setImageResource(iconRes)

            itemView.setOnClickListener {
                onCategoryClick(category)
            }
        }

        private fun getCategoryIconResource(iconName: String): Int {
            // Map category icon names to drawable resources
            return when (iconName.lowercase().replace(" ", "_")) {
                "team_building" -> R.drawable.ic_team
                "sports" -> R.drawable.ic_sports
                "workshops" -> R.drawable.ic_workshops
                "happy_fridays" -> R.drawable.ic_happy_friday
                "cultural" -> R.drawable.ic_cultural
                "wellness" -> R.drawable.ic_wellness
                else -> R.drawable.ic_launcher_foreground // Fallback icon
            }
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}