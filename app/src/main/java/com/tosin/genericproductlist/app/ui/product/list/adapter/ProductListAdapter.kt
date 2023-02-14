package com.tosin.genericproductlist.app.ui.product.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.delegate.onItemClicked
import com.tosin.genericproductlist.domain.model.Product

class ProductListAdapter(private val delegate: onItemClicked<Product>) :
    ListAdapter<Product, ProductViewHolder>(diffCallback) {

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

            val resources = holder.itemView.resources

            val discount = String.format(
                resources.getString(R.string.discount_placeholder),
                product.discountPercentage
            )
            holder.discount.text = discount

            val price = String.format(
                resources.getString(R.string.price_placeholder),
                product.price
            )
            holder.price.text = price

            holder.itemView.setOnClickListener {
                delegate(product)
            }
        }
    }
}
