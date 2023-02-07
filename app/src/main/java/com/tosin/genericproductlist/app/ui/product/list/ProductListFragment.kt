package com.tosin.genericproductlist.app.ui.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.ui.interfaces.ImplementMethodsOnScreen
import com.tosin.genericproductlist.app.ui.product.list.adapter.ProductListAdapter
import com.tosin.genericproductlist.app.ui.viewModelFactory
import com.tosin.genericproductlist.databinding.FragmentProductListBinding
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
        mAdapter = ProductListAdapter()

        mAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            val showList = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            val showLoading = loadState.source.refresh is LoadState.Loading

            // here only hide loading. The show is in launch search/load
            showLoading(showLoading)
//            showLayoutEmptyList(showList && mAdapter.itemCount == 0)
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
                    ProductListDataSource()
                )
            }
        )[ProductListViewModel::class.java]
    }

    private fun setUpJob() {
        job?.cancel()
        job = lifecycleScope.launch {
            showLoading(true)

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

    private fun showLoading(show: Boolean) {
        _binding?.refreshProductList?.isRefreshing = show
    }
}
