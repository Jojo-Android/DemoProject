package com.example.demoproject.ui.myProduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.data.PreferencesHelper
import com.example.demoproject.model.ProductEntity
import com.example.demoproject.repository.AuthRepository
import com.example.demoproject.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProductViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val productRepository: ProductRepository,
    private val preferencesHelper: PreferencesHelper,
) : ViewModel() {
    private val _myProductUiStateLiveData = MutableLiveData<MyProductUiState>()
    val myProductUiStateLiveData: LiveData<MyProductUiState>
        get() = _myProductUiStateLiveData

    private val _selectedProductsEntity = MutableLiveData<Set<ProductEntity>>(emptySet())
    val selectedProductsEntity: LiveData<Set<ProductEntity>>
        get() = _selectedProductsEntity

    fun fetchProductsEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            _myProductUiStateLiveData.postValue(MyProductUiState(isLoading = true))
            try {
                val userId = preferencesHelper.getUserId()
                val data = productRepository.getMyProduct(userId)
                val selectedIds = _selectedProductsEntity.value?.map { it.id } ?: emptyList()
                val updatedProducts = data.map { product ->
                    product.copy(isSelected = selectedIds.contains(product.id))
                }
                _myProductUiStateLiveData.postValue(
                    MyProductUiState(
                        data = updatedProducts,
                        isLoading = false
                    )
                )
            } catch (e: Exception) {
                Log.e("MyProductViewModel", "Error loading my product", e)
                _myProductUiStateLiveData.postValue(
                    MyProductUiState(
                        error = e.message,
                        isLoading = false
                    )
                )
            }
        }
    }

    fun deleteSelectedProductsEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedProductsEntity.value?.forEach { productEntity ->
                productRepository.deleteProduct(productEntity)
            }
            _selectedProductsEntity.postValue(emptySet())
            fetchProductsEntity()
        }
    }

    fun onProductSelected(productEntity: ProductEntity, isSelected: Boolean) {
        val currentSelected = _selectedProductsEntity.value?.toMutableSet() ?: mutableSetOf()
        if (isSelected) {
            currentSelected.add(productEntity)
        } else {
            currentSelected.remove(productEntity)
        }
        _selectedProductsEntity.value = currentSelected
    }

    fun logout() {
        authRepository.logout()
    }
}