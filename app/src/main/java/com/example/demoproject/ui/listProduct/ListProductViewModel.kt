package com.example.demoproject.ui.listProduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.data.PreferencesHelper
import com.example.demoproject.data.model.Product
import com.example.demoproject.data.repository.AuthRepository
import com.example.demoproject.data.repository.ProductRepository
import com.example.demoproject.util.LogMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val productRepository: ProductRepository,
    private val preferencesHelper: PreferencesHelper,
) : ViewModel() {

    private val _listProductUiState = MutableLiveData<ListProductUiState>()
    val listProductUiStateLiveData: LiveData<ListProductUiState>
        get() = _listProductUiState


    private val _selectedProducts = MutableLiveData<Set<Product>>(emptySet())
    val selectedProducts: LiveData<Set<Product>>
        get() = _selectedProducts

    companion object {
        private const val TAG = "ListProductViewModel"
    }

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val loadingState = ListProductUiState(isLoading = true)
            _listProductUiState.postValue(loadingState)

            try {
                val data = productRepository.getProduct()

                val selectedIds = _selectedProducts.value?.map { it.id } ?: emptyList()
                val updatedProducts = data.map { product ->
                    product.copy(isSelected = selectedIds.contains(product.id))
                }

                val getDataState = ListProductUiState(data = updatedProducts, isLoading = false)
                _listProductUiState.postValue(getDataState)

            } catch (e: Exception) {
                Log.e(TAG, LogMessages.ERROR_FETCH_PRODUCTS, e)
                _listProductUiState.postValue(
                    ListProductUiState(
                        error = e.message,
                        isLoading = false,
                    )
                )
            }
        }
    }


    fun saveSelectedProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = preferencesHelper.getUserId()
            if (userId != -1L) {
                _selectedProducts.value?.forEach { product ->
                    productRepository.saveProduct(userId, product)
                }
                _selectedProducts.postValue(emptySet())
                fetchProduct()
            }
        }
    }

    fun onProductSelected(product: Product, isSelected: Boolean) {
        viewModelScope.launch {
            val currentSelected = _selectedProducts.value?.toMutableSet() ?: mutableSetOf()
            if (isSelected) {
                currentSelected.add(product)
            } else {
                currentSelected.remove(product)
            }
            _selectedProducts.value = currentSelected
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.logout()
        }
    }
}