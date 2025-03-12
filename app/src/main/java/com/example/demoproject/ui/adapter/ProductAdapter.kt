package com.example.demoproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.demoproject.databinding.ListItemBinding
import com.example.demoproject.data.model.Product
import javax.inject.Inject

class ProductAdapter @Inject constructor() :
    ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {

    private var productSelectedListener: ((Product, Boolean) -> Unit)? = null

    fun setProductSelectedListener(listener: (Product, Boolean) -> Unit) {
        productSelectedListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val data = getItem(position)
        holder.bind(data, productSelectedListener)
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldProduct: Product, newProduct: Product): Boolean {
            return oldProduct.id == newProduct.id
        }

        override fun areContentsTheSame(oldProduct: Product, newProduct: Product): Boolean {
            return oldProduct == newProduct
        }
    }
}



