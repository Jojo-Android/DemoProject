package com.example.demoproject.util

import android.content.Context
import android.widget.Toast
import javax.inject.Singleton

@Singleton
object ToastHelper {
    fun showToast(context: Context, message: String?, duration: Int = Toast.LENGTH_SHORT) {
        message?.let {
            Toast.makeText(context, it, duration).show()
        }
    }
}