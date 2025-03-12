package com.example.demoproject.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.demoproject.databinding.ListItemBinding
import com.example.demoproject.data.model.Product

class ProductViewHolder(private val binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, productSelectedListener: ((Product, Boolean) -> Unit)?) {
        binding.apply {
            textViewNameItem.text = product.title
            textViewPriceItem.text = "$${product.price}"
            imageViewProduct.load(product.images.firstOrNull()) { crossfade(true) }

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = product.isSelected

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                product.isSelected = isChecked
                productSelectedListener?.invoke(product, isChecked)
            }
        }
    }
}