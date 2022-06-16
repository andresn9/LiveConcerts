package com.android.liveconcerts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityTicketBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.objects.Ticket
import com.android.liveconcerts.recycler.EventAdapter
import com.android.liveconcerts.recycler.TicketAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class PayActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTicketBinding.inflate(layoutInflater) }
    private lateinit var ticketList : ArrayList<Ticket>
    private lateinit var db: FirebaseFirestore
    private lateinit var myAdapter: TicketAdapter
    private lateinit var recyclerView: RecyclerView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        var datos : Ticket
        var lista = binding.ticketRecycler
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        lista.setHasFixedSize(true)
        lista.layoutManager = LinearLayoutManager(this)



        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid




        recyclerView = binding.ticketRecycler

        ticketList = arrayListOf()

        myAdapter = TicketAdapter(ticketList)

        recyclerView.adapter = myAdapter
        myAdapter.notifyDataSetChanged()








        db.collection("userData").document(currentUser).collection("tickets").addSnapshotListener(object :
            EventListener<QuerySnapshot> {

            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {

                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }



                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {

                        ticketList.add(dc.document.toObject(Ticket::class.java))
                    }
                }

                myAdapter.notifyDataSetChanged()


            }

        })





        }
//            for(documento in it.result){
//                datos = /*"${documento.data}\n"*/  documento.toObject(Ticket::class.java)
//                ticketList.add(datos)
//            }




    }
