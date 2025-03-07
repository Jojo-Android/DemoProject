package com.example.demoproject.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.demoproject.R
import com.example.demoproject.databinding.FragmentRegisterBinding
import com.example.demoproject.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            registerViewModel.registerUiState.observe(viewLifecycleOwner) { uiState ->
                when {
                    uiState.isLoading -> {
                        showLoading()
                    }

                    uiState.error != null -> {
                        hideLoading()
                        Toast.makeText(context, uiState.error.toString(), Toast.LENGTH_LONG).show()
                    }

                    uiState.isSuccess -> {
                        hideLoading()
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG)
                            .show()
                        view.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                }

            }
            registerViewModel.register(
                User(
                    name = binding.editTextName.text.toString(),
                    username = binding.editTextEmail.text.toString(),
                    password = binding.editTextPassword.text.toString(),
                )
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