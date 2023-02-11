package com.tosin.genericproductlist.app.ui.product.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.delegate.onItemClicked
import com.tosin.genericproductlist.app.ui.interfaces.ImplementMethodsOnScreen
import com.tosin.genericproductlist.app.ui.product.detail.ProductDetailFragment
import com.tosin.genericproductlist.app.ui.product.list.adapter.ProductListAdapter
import com.tosin.genericproductlist.app.ui.state.UiProductState
import com.tosin.genericproductlist.app.ui.state.UiState
import com.tosin.genericproductlist.app.ui.viewModelFactory
import com.tosin.genericproductlist.data.database.datasource.DoQueriesToLoadProduct
import com.tosin.genericproductlist.databinding.FragmentProductListBinding
import com.tosin.genericproductlist.domain.data.ProductRepository
import com.tosin.genericproductlist.domain.model.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ProductListFragment : Fragment(R.layout.fragment_product_list), ImplementMethodsOnScreen {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductListViewModel
    private var job: Job? = null
    private lateinit var mAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
//        setUpMenu()
        setUpObserverViewModel()
        setUpJob()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }

    override fun setUpView() {
        mAdapter = ProductListAdapter(delegate)

        mAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            val showList = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            val showLoading = loadState.source.refresh is LoadState.Loading

            // here only hide loading. The show is in launch search/load
            if (showLoading) {
                showUiState(UiProductState(UiState.LOADING))
            } else if (showList && mAdapter.itemCount == 0) {
                showUiState(UiProductState(UiState.EMPTY_LIST))
            } else if (showList && mAdapter.itemCount > 0) {
                showUiState(UiProductState(UiState.DONE))
            }
        }

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)

        _binding?.recyclerViewProductList?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            addItemDecoration(dividerItemDecoration)
        }

        _binding?.refreshProductList?.setOnRefreshListener {
            setUpJob()
        }
    }

    override fun setUpMenu() {
        TODO("Not yet implemented")
    }

    override fun setUpObserverViewModel() {
        viewModel = ViewModelProvider(
            viewModelStore,
            viewModelFactory {
                ProductListViewModel(
                    ProductRepository(
                        DoQueriesToLoadProduct()
                    )
                )
            }
        )[ProductListViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    showUiState(uiState)
                }
            }
        }
    }

    private val delegate = object : onItemClicked<Product> {
        override fun invoke(itemClicked: Product) {
            if (_binding?.slidingPaneLayout?.isSlideable == true && _binding?.slidingPaneLayout?.isOpen == false) {
//ProductListFragment
                findNavController().navigate(R.id.productDetailFragment)
            }
        }
    }

    private fun setUpJob() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.loadProducts().collectLatest {
                mAdapter.submitData(it)
            }
        }

        // Scroll to top when the list is refreshed.
        lifecycleScope.launch {
            if (::mAdapter.isInitialized) {
                mAdapter.loadStateFlow
                    // Only emit when REFRESH LoadState for RemoteMediator changes.
                    .distinctUntilChangedBy { it.refresh }
                    // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { _binding?.recyclerViewProductList?.scrollToPosition(0) }
            }
        }
    }

    private fun showUiState(uiState: UiProductState) {
        println(uiState.uiState.name)
        Log.d("UiState", uiState.uiState.name)
        when (uiState.uiState) {
            UiState.DONE -> {
                _binding?.refreshProductList?.isRefreshing = false
            }
            UiState.EMPTY_LIST -> {
                _binding?.refreshProductList?.isRefreshing = false
            }
            UiState.EMPTY_SEARCH -> {
                _binding?.refreshProductList?.isRefreshing = false
            }
            UiState.ERROR -> {
                _binding?.refreshProductList?.isRefreshing = false
            }
            UiState.LOADING -> {
                _binding?.refreshProductList?.isRefreshing = true
            }
        }
    }
}
