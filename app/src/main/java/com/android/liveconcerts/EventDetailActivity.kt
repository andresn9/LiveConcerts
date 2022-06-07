package com.android.liveconcerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityLoginBinding
import com.android.liveconcerts.objects.Artist
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class EventDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEventDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val artist = intent.getParcelableExtra<Artist>("artist")
        if (artist !=null){
            val text = binding.artistName
            val image = binding.artistImage

            text.text = artist.name
            image.setImageResource(artist.image)
        }

        binding.btnPaypal.setOnClickListener{

            var intent = Intent (this, PayPalActivity::class.java)
            startActivity(intent)

        }
    }
}