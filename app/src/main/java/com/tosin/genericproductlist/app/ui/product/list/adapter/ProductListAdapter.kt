package com.tosin.genericproductlist.app.ui.product.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.domain.model.Product

class ProductListAdapter : PagingDataAdapter<Product, ProductViewHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Product,
                newItem: Product
            ): Boolean =
                oldItem.id == newItem.id && oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        getItem(position)?.let { product ->
            holder.code.text = product.id.toString()
            holder.title.text = product.title
            holder.brand.text = product.brand

            val discount = "${product.discountPercentage}%"
            holder.discount.text = discount

            val price = "$ ${product.price}"
            holder.price.text = price
        }
    }
}
