package com.tosin.genericproductlist.app.ui.product.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tosin.genericproductlist.AppApplication
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.ui.interfaces.ImplementMethodsOnScreen
import com.tosin.genericproductlist.app.ui.viewModelFactory
import com.tosin.genericproductlist.data.datastore.SettingsDataStore
import com.tosin.genericproductlist.data.datastore.dataStore
import com.tosin.genericproductlist.databinding.FragmentProductDetailBinding
import com.tosin.genericproductlist.domain.data.ProductRepository
import com.tosin.genericproductlist.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail), ImplementMethodsOnScreen {

    private val uiScope = CoroutineScope(Job() + IO)

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var layoutDataStore: SettingsDataStore

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

        setUpView()
//        setUpMenu()
        setUpObserverViewModel()
    }

    override fun setUpView() {
        layoutDataStore = SettingsDataStore(requireContext().dataStore)
    }

    override fun setUpMenu() {
        TODO("Not yet implemented")
    }

    override fun setUpObserverViewModel() {
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory {
                ProductDetailViewModel(
                    ProductRepository(
                        (requireActivity().application as AppApplication).database.productDao()
                    )
                )
            }
        )[ProductDetailViewModel::class.java]

        viewModel.product.observe(viewLifecycleOwner) {
            showProductOnScreen(it)
        }

        uiScope.launch {
            layoutDataStore.preferenceFlow.collectLatest {
                viewModel.loadProductById(it)
            }
        }
    }

    private fun showProductOnScreen(product: Product) {
        _binding?.textViewProductDetailName?.text = product.title
        _binding?.textViewProductDetailBrand?.text = product.brand
        _binding?.textViewtextViewProductDetailDescription?.text = product.description

        _binding?.textViewProductDetailRating?.text = product.rating.toString()

        val discount = String.format(
            resources.getString(R.string.discount_placeholder),
            product.discountPercentage
        )
        _binding?.textViewProductDetailDiscount?.text = discount

        val price = String.format(
            resources.getString(R.string.price_placeholder),
            product.price
        )
        _binding?.textViewProductDetailPrice?.text = price

        if (product.images.isNotEmpty()) {
            Glide
                .with(requireContext())
                .load(product.images.first())
                .error(R.drawable.baseline_cloud_off_24)
                .into(binding.imageProductDetail)
        }
    }
}
