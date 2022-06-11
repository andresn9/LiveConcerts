package com.android.liveconcerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityTicketBinding
import com.android.liveconcerts.recycler.EventAdapter
import com.google.firebase.firestore.FirebaseFirestore

class TicketActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTicketBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var datos = ""
        var lista = binding.ticketList
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()


        db.collection("tickets").get().addOnSuccessListener {
            for(documento in it){
                datos += "${documento.data}\n"
            }
            binding.textView4.text = datos
        }
    }
}