package com.example.demoproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.demoproject.databinding.ListMyProductItemBinding
import com.example.demoproject.model.Product
import com.example.demoproject.model.ProductEntity
import javax.inject.Inject

class MyProductAdapter @Inject constructor() :
    ListAdapter<ProductEntity, MyProductAdapter.MyProductViewHolder>(MyProductDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListMyProductItemBinding.inflate(inflater, parent, false)
        return MyProductViewHolder(binding)
    }

    private var productSelectedListener: ((ProductEntity, Boolean) -> Unit)? = null

    fun setProductSelectedListener(listener: (ProductEntity, Boolean) -> Unit) {
        productSelectedListener = listener
    }

    override fun onBindViewHolder(
        holder: MyProductViewHolder,
        position: Int
    ) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class MyProductViewHolder(private val binding: ListMyProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productEntity: ProductEntity) {
            binding.apply {
                textViewNameItem.text = productEntity.title
                textViewPriceItem.text = productEntity.price.toString()
                imageViewProduct.load(productEntity.image) {
                    crossfade(true)
                }

                checkBox.isChecked = productEntity.isSelected

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    productEntity.isSelected = isChecked
                    productSelectedListener?.invoke(productEntity, isChecked)
                }
            }
        }
    }
}


class MyProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
    override fun areItemsTheSame(
        oldProductEntity: ProductEntity,
        newProductEntity: ProductEntity,
    ): Boolean {
        return oldProductEntity.id == newProductEntity.id
    }

    override fun areContentsTheSame(
        oldProductEntity: ProductEntity,
        newProductEntity: ProductEntity,
    ): Boolean {
        return oldProductEntity == newProductEntity
    }

}

