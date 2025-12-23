package com.example.crosscollab.presentation.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crosscollab.R

class FaqAdapter(
    private val onFaqClick: (Faq) -> Unit
) : ListAdapter<Faq, FaqAdapter.FaqViewHolder>(FaqDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(view)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FaqViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        private val tvAnswer: TextView = itemView.findViewById(R.id.tvAnswer)
        private val ivExpandIcon: ImageView = itemView.findViewById(R.id.ivExpandIcon)

        fun bind(faq: Faq) {
            tvQuestion.text = faq.question
            tvAnswer.text = faq.answer

            // Show/hide answer based on expanded state
            tvAnswer.visibility = if (faq.isExpanded) View.VISIBLE else View.GONE

            // Rotate icon based on expanded state with animation
            val rotation = if (faq.isExpanded) 180f else 0f
            ivExpandIcon.rotation = rotation

            itemView.setOnClickListener {
                // Toggle expanded state
                faq.isExpanded = !faq.isExpanded

                // Animate the icon rotation
                animateIconRotation(ivExpandIcon, !faq.isExpanded)

                // Update visibility with animation
                if (faq.isExpanded) {
                    tvAnswer.visibility = View.VISIBLE
                } else {
                    tvAnswer.visibility = View.GONE
                }

                // Notify adapter to update the item
                notifyItemChanged(bindingAdapterPosition)

                // Callback
                onFaqClick(faq)
            }
        }

        private fun animateIconRotation(view: ImageView, collapse: Boolean) {
            val fromRotation = if (collapse) 180f else 0f
            val toRotation = if (collapse) 0f else 180f

            val rotate = RotateAnimation(
                fromRotation, toRotation,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 200
            rotate.fillAfter = true
            view.startAnimation(rotate)
        }
    }

    private class FaqDiffCallback : DiffUtil.ItemCallback<Faq>() {
        override fun areItemsTheSame(oldItem: Faq, newItem: Faq): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Faq, newItem: Faq): Boolean {
            return oldItem == newItem
        }
    }
}