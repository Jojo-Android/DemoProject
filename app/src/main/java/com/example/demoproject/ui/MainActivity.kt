package com.example.demoproject.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.demoproject.R
import com.example.demoproject.data.PreferencesHelper
import com.example.demoproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferencesHelper: PreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val userId = preferencesHelper.getUserId()

        if (userId != -1L) {
            navController.navigate(R.id.action_loginFragment_to_ListProductFragment)
        } else {
            navController.navigate(R.id.loginFragment)
        }
    }

}