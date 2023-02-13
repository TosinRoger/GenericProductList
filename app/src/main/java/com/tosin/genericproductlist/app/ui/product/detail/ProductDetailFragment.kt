package com.tosin.genericproductlist.app.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.data.database.datasource.DoQueriesToLoadProduct
import com.tosin.genericproductlist.databinding.FragmentProductDetailBinding
import com.tosin.genericproductlist.domain.data.ProductRepository

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    companion object {
        const val PRODUCT_ID = "product_id"
    }

    fun newInstance(productId: Int): ProductDetailFragment {
        return ProductDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(PRODUCT_ID, productId)
            }
        }
    }

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getInt(PRODUCT_ID) ?: 1
        loadProduct(productId)
    }

    private fun loadProduct(productId: Int) {
        val productRepository = ProductRepository(
            DoQueriesToLoadProduct()
        )
        val product = productRepository.findById(productId)

        product?.let {
            _binding?.textViewProductDetailName?.text = it.title
        }
    }
}
