package com.example.demoproject.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.demoproject.R
import com.example.demoproject.util.ToastHelper
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
        savedInstanceState: Bundle?,
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
                    ToastHelper.showToast(requireContext(), uiState.error.toString())
                }

                uiState.isSuccess -> {
                    hideLoadingDialog()
                    ToastHelper.showToast(
                        requireContext(),
                        getString(R.string.toast_login_successful)
                    )
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
            loginViewModel.setUsername(text.toString())
        }

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setPassword(text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingDialog() {
        if (loadingDialog?.isShowing == true) return

        val progressBar = CircularProgressIndicator(requireContext()).apply {
            isIndeterminate = true
        }

        loadingDialog =
            MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.title_dialog_logging_in))
                .setMessage(getString(R.string.message_login_dialog)).setView(progressBar)
                .setCancelable(false).create()

        loadingDialog?.show()
    }


    private fun hideLoadingDialog() {
        loadingDialog?.dismiss()
    }
}
