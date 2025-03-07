package com.example.demoproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.demoproject.R
import com.example.demoproject.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.loginUiStateLiveData.observe(viewLifecycleOwner) { uiState ->
            when {
                uiState.isLoading -> {
                    showLoading()
                }

                uiState.error != null -> {
                    hideLoading()
                    Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                uiState.isSuccess -> {
                    hideLoading()
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT)
                        .show()
                    view.findNavController().navigate(R.id.action_loginFragment_to_ListProductFragment)
                }
            }
        }
        binding.textViewCreateAccount.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.buttonLogin.setOnClickListener {
            loginViewModel.login(
                username = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString(),
            )
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}