package edu.unikom.tubesuas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.unikom.tubesuas.data.WalkthroughItem
import edu.unikom.tubesuas.databinding.ItemWalkthroughSliderBinding

class WalkthroughAdapter(private val items: List<WalkthroughItem>) : RecyclerView.Adapter<WalkthroughAdapter.WalkthroughViewHolder>() {

    inner class WalkthroughViewHolder(private val binding: ItemWalkthroughSliderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WalkthroughItem) {
            binding.sliderImageView.setImageResource(item.image)
            binding.sliderDescriptionText.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkthroughViewHolder {
        // Membuat binding untuk layout item_walkthrough_slider.xml
        val binding = ItemWalkthroughSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalkthroughViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalkthroughViewHolder, position: Int) {
        // Mengambil data dari daftar berdasarkan posisinya, lalu memanggil fungsi bind
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}