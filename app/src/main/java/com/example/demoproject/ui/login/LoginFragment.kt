package com.example.demoproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.demoproject.R
import com.example.demoproject.databinding.FragmentLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    private var loadingDialog: androidx.appcompat.app.AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupTextWatchers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginUiStateLiveData.observe(viewLifecycleOwner) { uiState ->
            when {
                uiState.isLoading -> showLoadingDialog()
                uiState.error != null -> {
                    hideLoadingDialog()
                    Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG).show()
                }

                uiState.isSuccess -> {
                    hideLoadingDialog()
                    Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                    view.findNavController()
                        .navigate(R.id.action_loginFragment_to_ListProductFragment)
                }
            }
        }

        binding.textViewCreateAccount.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            loginViewModel.login(username, password)
        }
    }

    private fun setupTextWatchers() {
        binding.editTextUsername.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setUsername(text.toString())  // Update ViewModel with username
        }

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setPassword(text.toString())  // Update ViewModel with password
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingDialog() {
        if (loadingDialog == null) {
            val progressBar = CircularProgressIndicator(requireContext()).apply {
                isIndeterminate = true
            }

            loadingDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logging in")
                .setMessage("Please wait while we log you in.")
                .setView(progressBar)
                .setCancelable(false)
                .create()

            loadingDialog?.show()
        } else {
            loadingDialog?.show()
        }
    }

    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}
