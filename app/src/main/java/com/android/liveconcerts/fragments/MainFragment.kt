package com.android.liveconcerts.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.EventDetailActivity
import com.android.liveconcerts.R
import com.android.liveconcerts.databinding.FragmentEventsBinding
import com.android.liveconcerts.databinding.FragmentMainBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.recycler.EventAdapter
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

//    //Habilita lo que se muestra en el fragment
//    override fun onCreate(savedInstanceState: Bundle?) {
//            setHasOptionsMenu(true)
//            super.onCreate(savedInstanceState)
//        }
//
//        //Infla el contenido
//        override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//            inflater!!.inflate(R.menu.menu_main, menu)
//            super.onCreateOptionsMenu(menu, inflater)
//        }
//
//        //Habilita poder interactuar con los items en pantalla
//        override fun onOptionsItemSelected(item: MenuItem): Boolean {
//            val id = item!!.itemId
//
//            if (id == R.id.action_settings){
//            Toast.makeText(activity, "A configuración", Toast.LENGTH_SHORT).show()
//        }
//        if (id == R.id.action_signOut){
//            Toast.makeText(activity, "Cerrando sesión", Toast.LENGTH_SHORT).show()
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }
//
//}


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsList: ArrayList<Event>
    private lateinit var eventsAdapter: EventAdapter

    var events = arrayListOf<Event>()

    //Habilita lo que se muestra en el fragment
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        Thread(Runnable {
            readJson()
        }).start()
    }

    //Infla el contenido
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //Habilita poder interactuar con los items en pantalla
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item!!.itemId

        if (id == R.id.action_settings) {
            Toast.makeText(activity, "A configuración", Toast.LENGTH_SHORT).show()
        }
        if (id == R.id.action_signOut) {
            Toast.makeText(activity, "Cerrando sesión", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerSearch
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventsList = ArrayList()

//        eventsList.add(Event(R.drawable.logomad , "MadCool", "Madrid", "5/7/22", 180))
//        eventsList.add(Event(R.drawable.logoand , "Andalucía Big Festival", "Málaga", "5/7/22", 120))
//        eventsList.add(Event(R.drawable.logoresu , "Resurrection", "Galicia", "5/7/22", 150))
//        eventsList.add(Event(R.drawable.logops , "Primavera Sound", "Barcelona", "5/7/22", 180))
//        eventsList.add(Event(R.drawable.logocala , "Calamijas", "Málaga", "5/7/22", 100))

        eventsAdapter = EventAdapter(events)
        recyclerView.adapter = eventsAdapter

        eventsAdapter.onItemClick = {
            val intent = Intent(activity, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }

        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun readJson() {


        var json: String? = null
        val jsonArr: JSONArray? = null
        try {
            val inputStream: InputStream = activity?.assets!!.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArr = JSONArray(json)

            Log.i("artists", jsonArr.length().toString())


            for (i in 0..jsonArr.length() - 1) {
                var eventArray = jsonArr.getJSONObject(i).getJSONArray("events")
                var image = jsonArr.getJSONObject(i).getString("image")


                for (e in 0..eventArray.length() - 1) {

                    if (eventArray.toString() != "[]") {


                        var name = eventArray.getJSONObject(e).getString("eventName")
                        var location = eventArray.getJSONObject(e).getString("location")
                        var price = eventArray.getJSONObject(e).getString("price")
                        var date = eventArray.getJSONObject(e).getString("date")

                        var event = Event(image, name, location, date, price)
                        events.add(event)
                    }


                }


            }


            events.sortBy { convertDate(it.date) }

        } catch (e: Exception) {

        }


    }

    val URL.toBitmap: Bitmap?
        get() {
            return try {
                BitmapFactory.decodeStream(openStream())
            } catch (e: IOException) {
                null
            }
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(dateInString: String?): LocalDate? {
        var simpleFormat =  DateTimeFormatter.ISO_DATE;
        var convertedDate = LocalDate.parse(dateInString, simpleFormat)
        return convertedDate
    }


}