package com.example.demoproject.ui.myProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoproject.R
import com.example.demoproject.adapter.MyProductAdapter
import com.example.demoproject.databinding.FragmentMyProductBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyProductFragment : Fragment(R.layout.fragment_my_product) {
    private lateinit var binding: FragmentMyProductBinding

    private val myProductViewModel: MyProductViewModel by viewModels()

    @Inject
    lateinit var myProductAdapter: MyProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myProductViewModel.fetchProductsEntity()
        observerMyProduct()

        binding.apply {
            recyclerViewMyProduct.apply {
                adapter = myProductAdapter
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
                    R.id.product -> {
                        view.findNavController()
                            .navigate(R.id.action_myProductFragment_to_ListProductFragment)
                        true
                    }

                    R.id.logout -> {
                        myProductViewModel.logout()
                        view.findNavController()
                            .navigate(R.id.action_myProductFragment_to_loginFragment)
                        Toast.makeText(context, "Logout successful!", Toast.LENGTH_LONG).show()
                        true
                    }

                    else -> false
                }
            }


            myProductAdapter.setProductSelectedListener { productEntity, isSelected ->
                myProductViewModel.onProductSelected(productEntity, isSelected)
            }

            myProductViewModel.selectedProductsEntity.observe(
                viewLifecycleOwner
            ) { selectedProductsEntity ->
                binding.buttonDelete.visibility =
                    if (selectedProductsEntity.isNotEmpty()) View.VISIBLE else View.GONE
            }
            binding.buttonDelete.setOnClickListener {
                myProductViewModel.deleteSelectedProductsEntity()

                Toast.makeText(context, "Products deleted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observerMyProduct() {
        myProductViewModel.myProductUiStateLiveData.observe(viewLifecycleOwner) { uiState ->
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
                    myProductAdapter.submitList(uiState.data)
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