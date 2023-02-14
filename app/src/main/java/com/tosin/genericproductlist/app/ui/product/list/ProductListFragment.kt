package com.tosin.genericproductlist.app.ui.product.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.delegate.onItemClicked
import com.tosin.genericproductlist.app.ui.interfaces.ImplementMethodsOnScreen
import com.tosin.genericproductlist.app.ui.product.detail.ProductDetailFragment
import com.tosin.genericproductlist.app.ui.product.list.adapter.ProductListAdapter
import com.tosin.genericproductlist.app.ui.state.UiProductState
import com.tosin.genericproductlist.app.ui.state.UiState
import com.tosin.genericproductlist.app.ui.viewModelFactory
import com.tosin.genericproductlist.data.dataStore
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
    private lateinit var mAdapter: ProductListAdapter
    private var job: Job? = null

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
        val slidingPaneLayout = binding.slidingPaneLayout
//        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        // Connect the SlidingPaneLayout to the system back button.

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            ProductListOnBackPressedCallback(slidingPaneLayout)
        )

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
            lifecycleScope.launch {
                requireContext().dataStore.edit { settings ->
                    settings[ProductDetailFragment.SAVE_PRODUCT_ID] = itemClicked.id
                }
                _binding?.slidingPaneLayout?.openPane()
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

class ProductListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    // Set the default 'enabled' state to true only if it is slidable (i.e., the panes
    // are overlapping) and open (i.e., the detail pane is visible).
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
),
    SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        // Return to the list pane when the system back button is pressed.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        // Churros
    }

    override fun onPanelOpened(panel: View) {
        // Intercept the system back button when the detail pane becomes visible.
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        // Disable intercepting the system back button when the user returns to the
        // list pane.
        isEnabled = false
    }
}
