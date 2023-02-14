package com.tosin.genericproductlist.app.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.data.dataStore
import com.tosin.genericproductlist.data.database.datasource.DoQueriesToLoadProduct
import com.tosin.genericproductlist.databinding.FragmentProductDetailBinding
import com.tosin.genericproductlist.domain.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    companion object {
        private const val PRODUCT_ID = "product_id"
        val SAVE_PRODUCT_ID = intPreferencesKey(PRODUCT_ID)
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

        val productIdUpdateFlow: Flow<Int> = requireContext().dataStore.data
            .map { preferences ->
                val productId = preferences[SAVE_PRODUCT_ID] ?: 0
                productId
            }

        lifecycleScope.launch {
            productIdUpdateFlow.collectLatest {
                loadProduct(it)
            }
        }
    }

    /**
     * Called in fragment only to test pass [PRODUCT_ID] by data store
     */
    private fun loadProduct(productId: Int) {
        val productRepository = ProductRepository(
            DoQueriesToLoadProduct()
        )
        val product = productRepository.findById(productId)

        product?.let {
            _binding?.textViewProductDetailName?.text = it.title
            _binding?.textViewProductDetailBrand?.text = it.brand
            _binding?.textViewtextViewProductDetailDescription?.text = it.description

            _binding?.textViewProductDetailRating?.text = it.rating.toString()
            _binding?.textViewProductDetailDiscount?.text = "${it.discountPercentage}%"
            _binding?.textViewProductDetailPrice?.text = "$ ${it.price}"

            Glide
                .with(requireContext())
                .load(it.images.first())
                .error(R.drawable.baseline_cloud_off_24)
                .into(binding.imageProductDetail)
        }
    }
}
