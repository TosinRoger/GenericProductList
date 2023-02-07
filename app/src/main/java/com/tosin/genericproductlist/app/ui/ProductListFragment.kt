package com.tosin.genericproductlist.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tosin.genericproductlist.R
import com.tosin.genericproductlist.app.ui.interfaces.ImplementMethodsOnScreen
import com.tosin.genericproductlist.databinding.FragmentProductListBinding

class ProductListFragment : Fragment(R.layout.fragment_product_list), ImplementMethodsOnScreen {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

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

//        setUpView()
//        setUpMenu()
//        setUpObserverViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setUpView() {
        TODO("Not yet implemented")
    }

    override fun setUpMenu() {
        TODO("Not yet implemented")
    }

    override fun setUpObserverViewModel() {
        TODO("Not yet implemented")
    }
}
