package com.android.liveconcerts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityLoginBinding
import com.android.liveconcerts.objects.Artist
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.objects.Ticket
import com.google.firebase.firestore.FirebaseFirestore
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.math.BigDecimal

class EventDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEventDetailBinding.inflate(layoutInflater) }
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val event = intent.getParcelableExtra<Event>("event")
        if (event !=null){
            val text = binding.artistName
            val image = binding.artistImage
            val price = binding.eventPrice

            db = FirebaseFirestore.getInstance()

            text.text = event.name
            price.text = event.price.toString()+" €"
            image.setImageResource(event.image)
        }

        binding.btnPaypal.setOnClickListener{
            var ticket = Ticket(event?.name, event?.date, event?.price.toString())
            insertData(ticket)
            var intent = Intent (this, PayPalActivity::class.java)
            startActivity(intent)

        }
    }

    private fun insertData(ticket: Ticket){
        db.collection("tickets").add(ticket).addOnSuccessListener {
            Toast.makeText(this, "Entrada comprada", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Error al comprar la entrada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun readData(){
        db.collection("tickets").get().addOnCompleteListener {
            if (it.isSuccessful){

            }
        }
    }
}