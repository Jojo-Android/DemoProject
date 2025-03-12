package com.example.demoproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.demoproject.databinding.ListMyProductItemBinding
import com.example.demoproject.data.model.ProductEntity
import javax.inject.Inject

class MyProductAdapter @Inject constructor() :
    ListAdapter<ProductEntity, MyProductViewHolder>(MyProductDiffCallback()) {

    private var productSelectedListener: ((ProductEntity, Boolean) -> Unit)? = null

    fun setProductSelectedListener(listener: (ProductEntity, Boolean) -> Unit) {
        productSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
        val binding = ListMyProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data, productSelectedListener)
    }
}

class MyProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
        return oldItem == newItem
    }
}
