package com.android.liveconcerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityTicketBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.objects.Ticket
import com.android.liveconcerts.recycler.EventAdapter
import com.android.liveconcerts.recycler.TicketAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class TicketActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTicketBinding.inflate(layoutInflater) }
    private lateinit var ticketList : ArrayList<Ticket>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        lateinit var ticketsAdapter: TicketAdapter
        var datos : Ticket
        var lista = binding.ticketRecycler
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        lista.setHasFixedSize(true)
        lista.layoutManager = LinearLayoutManager(this)

        ticketList = ArrayList()


        ticketList.add(Ticket("MadCool","5/7/22", 80.toString()))
        ticketList.add(Ticket("Andalucía Big Festival", "5/7/22", 80.toString()))



        db.collection("tickets").get().addOnCompleteListener {
            for(documento in it.result){
                datos = /*"${documento.data}\n"*/  documento.toObject(Ticket::class.java)
                ticketList.add(datos)
            }
        }

        ticketsAdapter = TicketAdapter(ticketList)
        lista.adapter = ticketsAdapter

    }
}