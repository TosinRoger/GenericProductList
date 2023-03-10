package com.tosin.genericproductlist.app.ui.product.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tosin.genericproductlist.databinding.ItemProductBinding

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemProductBinding.bind(itemView)

    val title = binding.textViewItemProductTitle
    val code = binding.textViewItemProductCode
    val brand = binding.textViewItemProductBrand
    val discount = binding.textViewItemProductDiscount
    val price = binding.textViewItemProductPrice
}
