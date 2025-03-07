package com.example.demoproject.ui.listProduct

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoproject.R
import com.example.demoproject.adapter.ProductAdapter
import com.example.demoproject.databinding.FragmentProductListBinding
import com.example.demoproject.model.Product
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListProductFragment : Fragment(R.layout.fragment_product_list) {

    private lateinit var binding: FragmentProductListBinding

    private val listProductViewModel: ListProductViewModel by viewModels()

    @Inject
    lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listProductViewModel.fetchProduct()

        observerProduct()

        binding.apply {
            recyclerViewItemList.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(
                    this.context,
                    LinearLayoutManager.VERTICAL,
                    false,
                )
                addItemDecoration(
                    DividerItemDecoration(
                        this.context, DividerItemDecoration.VERTICAL
                    )
                )
            }

            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.myproduct -> {
                        view.findNavController()
                            .navigate(R.id.action_ListProductFragment_to_myProductFragment)
                        true
                    }

                    R.id.logout -> {
                        listProductViewModel.logout()
                        view.findNavController()
                            .navigate(R.id.action_ListProductFragment_to_loginFragment)
                        true
                    }

                    else -> false
                }
            }

            productAdapter.setProductSelectedListener { product, isSelected ->
                listProductViewModel.onProductSelected(product, isSelected)
            }
        }


        listProductViewModel.selectedProducts.observe(
            viewLifecycleOwner
        ) { selectedProducts ->
            binding.buttonSave.visibility =
                if (selectedProducts.isNotEmpty()) View.VISIBLE else View.GONE
        }

        binding.buttonSave.setOnClickListener {
            listProductViewModel.saveSelectedProducts()

            Toast.makeText(context, "Products saved!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observerProduct() {
        listProductViewModel.listProductUiStateLiveData.observe(viewLifecycleOwner) { uiState ->
            when {
                uiState.isLoading -> {
                    showLoading()
                    binding.textViewError.text = ""
                }

                uiState.error != null -> {
                    hideLoading()
                    binding.textViewError.text = getString(R.string.error_message_general)
                    Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG).show()
                }

                !uiState.data.isNullOrEmpty() -> {
                    hideLoading()
                    productAdapter.submitList(uiState.data)
                    binding.textViewError.text = ""
                }

                else -> {
                    hideLoading()
                    binding.textViewError.text = getString(R.string.no_data_message)
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

}