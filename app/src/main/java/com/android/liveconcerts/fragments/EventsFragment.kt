package com.android.liveconcerts.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.EventDetailActivity
import com.android.liveconcerts.R
import com.android.liveconcerts.databinding.FragmentEventsBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.recycler.EventAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [EventsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private lateinit var eventsList : ArrayList<Event>
    private lateinit var eventsAdapter: EventAdapter

    //Habilita lo que se muestra en el fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //Infla el contenido
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Habilita poder interactuar con los items en pantalla
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if (id == R.id.action_settings){
            Toast.makeText(activity, "A configuración", Toast.LENGTH_SHORT).show()
        }
        if (id == R.id.action_signOut){
            Toast.makeText(activity, "Cerrando sesión", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerEvents
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventsList = ArrayList()

        eventsList.add(Event(R.drawable.code_android_logo , "MadCool", "Madrid", "5/7/22", 80))
        eventsList.add(Event(R.drawable.code_android_logo , "Andalucía Big Festival", "Madrid", "5/7/22", 80))
        eventsList.add(Event(R.drawable.code_android_logo , "Resurrection", "Madrid", "5/7/22", 80))
        eventsList.add(Event(R.drawable.code_android_logo , "Las ketchu", "Madrid", "5/7/22", 80))
        eventsList.add(Event(R.drawable.code_android_logo , "Los manolos", "Madrid", "5/7/22", 80))

        eventsAdapter = EventAdapter(eventsList)
        recyclerView.adapter = eventsAdapter

        eventsAdapter.onItemClick = {
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        return view
    }
}