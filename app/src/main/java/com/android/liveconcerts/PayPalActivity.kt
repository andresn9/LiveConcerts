package com.android.liveconcerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.liveconcerts.databinding.ActivityMainBinding
//import com.android.liveconcerts.databinding.ActivityPayPalBinding
import com.android.liveconcerts.objects.UserInfo
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class PayPalActivity : AppCompatActivity() {
    private var config: PayPalConfiguration? = null
    private var amount: Double= 20.54
   // private val binding by lazy { ActivityPayPalBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        //binding.totalV.text = amount.toString()

        config = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(
            UserInfo.clientId)
        var i = Intent (this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(i)

        //getResult.launch(intent)

        //binding.btnPaypal.setOnClickListener{

            //amount = binding.totalV.text.toString().toDouble()

            var payment = PayPalPayment (BigDecimal.valueOf(amount), "EUR", "Venta de entradas", PayPalPayment.PAYMENT_INTENT_SALE)
            var intent = Intent (this, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment)
            startActivity(intent)

        }

    }

    //override fun onDestroy() {
      //  stopService(Intent(this, PayPalService::class.java))
      //  super.onDestroy()
   // }
