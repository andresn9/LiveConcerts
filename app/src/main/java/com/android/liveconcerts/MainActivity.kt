package com.android.liveconcerts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.android.liveconcerts.databinding.ActivityMainBinding
import com.android.liveconcerts.objects.UserInfo
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private var config: PayPalConfiguration? = null
    private var amount: Double= 20.54
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.totalV.text = amount.toString()

        config = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.clientId)
        var i = Intent (this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(i)

        binding.btnPaypal.setOnClickListener{

            val getResult = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {

                    val value = it.data?.getStringExtra("input")

                }
            }

            amount = binding.totalV.text.toString().toDouble()

            var payment = PayPalPayment (BigDecimal.valueOf(amount), "EUR", "Venta de entradas", PayPalPayment.PAYMENT_INTENT_SALE)
            var intent = Intent (this, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivity(intent)
            //getResult.launch(intent)


        }

    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}