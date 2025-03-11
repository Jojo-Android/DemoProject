package com.example.demoproject.adapter

import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.demoproject.databinding.ListMyProductItemBinding
import com.example.demoproject.model.ProductEntity

class MyProductViewHolder(private val binding: ListMyProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        productEntity: ProductEntity,
        productSelectedListener: ((ProductEntity, Boolean) -> Unit)?
    ) {
        binding.apply {
            textViewNameItem.text = productEntity.title
            textViewPriceItem.text = productEntity.price.toString()
            imageViewProduct.load(productEntity.image) { crossfade(true) }

            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = productEntity.isSelected

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                productEntity.isSelected = isChecked
                productSelectedListener?.invoke(productEntity, isChecked)
            }
        }
    }
}