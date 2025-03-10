package com.example.demoproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.demoproject.databinding.ListItemBinding
import com.example.demoproject.model.Product
import javax.inject.Inject

class ProductAdapter @Inject constructor() :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

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
        holder.bind(data)
    }

    inner class ProductViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                textViewNameItem.text = product.title
                textViewPriceItem.text = product.price.toString()
                imageViewProduct.load(product.images[0]) {
                    crossfade(true)
                }

                checkBox.isChecked = product.isSelected

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    product.isSelected = isChecked
                    productSelectedListener?.invoke(product, isChecked)
                }
            }
        }
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



