package com.android.liveconcerts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.liveconcerts.databinding.ActivityMainBinding
import com.paypal.android.sdk.payments.PayPalService

enum class ProviderType{
    BASIC //tipo de autenticación básica con email y contraseña
}

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}