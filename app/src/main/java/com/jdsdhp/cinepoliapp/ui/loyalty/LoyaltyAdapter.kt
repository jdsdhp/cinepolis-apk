package com.jdsdhp.cinepoliapp.ui.loyalty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.mappers.Transaction
import com.jdsdhp.cinepoliapp.databinding.ItemTransactionBinding
import com.jdsdhp.cinepoliapp.util.roundDecimals
import com.jdsdhp.cinepoliapp.util.toDate

class LoyaltyAdapter : RecyclerView.Adapter<LoyaltyAdapter.ViewHolder>() {

    private var items: List<Transaction> = listOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    fun submitList(items: List<Transaction>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val notAvailableText = itemView.context.getString(R.string.no_available)

        fun bind(transaction: Transaction) = binding.run {
            titleView.text = transaction.cinema?:notAvailableText
            subtitleView.text = transaction.message?:notAvailableText
            pointsView.text = transaction.points.roundDecimals().toString()
            dateView.text = transaction.date.toDate()
        }

    }

}
