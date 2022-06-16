package com.android.liveconcerts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.databinding.ActivityArtistBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.recycler.EventAdapter
import org.json.JSONArray
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors

class ArtistActivity : AppCompatActivity() {

    private val binding by lazy { ActivityArtistBinding.inflate(layoutInflater) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsList: ArrayList<Event>
    private lateinit var eventsAdapter: EventAdapter
    private lateinit var image : String
    var mImage: Bitmap? = null
    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())
    var events = arrayListOf<Event>()
    lateinit var artistName : String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mIntent = intent
        var artistImage: String? = mIntent.getStringExtra("artistaImage")
        artistName= mIntent.getStringExtra("artistaName").toString()

        Thread(Runnable {
            readJson()
        }).start()

        recyclerView = binding.recyclerEvents2
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        eventsList = ArrayList()

//        eventsList.add(Event(R.drawable.logomad , "MadCool", "Madrid", "5/7/22", 180))
//        eventsList.add(Event(R.drawable.logoand , "Andalucía Big Festival", "Málaga", "5/7/22", 120))
//        eventsList.add(Event(R.drawable.logoresu , "Resurrection", "Galicia", "5/7/22", 150))
//        eventsList.add(Event(R.drawable.logops , "Primavera Sound", "Barcelona", "5/7/22", 180))
//        eventsList.add(Event(R.drawable.logocala , "Calamijas", "Málaga", "5/7/22", 100))

        eventsAdapter = EventAdapter(events)
        recyclerView.adapter = eventsAdapter

        eventsAdapter.onItemClick = {
            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("event", it)
            startActivity(intent)
        }


        var imageBit : Bitmap? = null
        binding.artistName.text = artistName


        myExecutor.execute {
            mImage = artistImage?.let { mLoad(it) }
            myHandler.post {
                binding.artistImage.setImageBitmap(mImage)

            }
        }


        setContentView(binding.root)

//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        val fragment = EventsFragment()
//        fragmentTransaction.add(R.id.list, fragment)
//        fragmentTransaction.commit()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun readJson() {


        var json: String? = null
        val jsonArr: JSONArray? = null
        try {
            val inputStream: InputStream = assets!!.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArr = JSONArray(json)

            Log.i("artists", jsonArr.length().toString())


            for (i in 0..jsonArr.length() - 1) {
                var eventArray = jsonArr.getJSONObject(i).getJSONArray("events")
                var image = jsonArr.getJSONObject(i).getString("image")
                var nameToCheck = jsonArr.getJSONObject(i).getString("name")


                for (e in 0..eventArray.length() - 1) {

                    if (eventArray.toString() != "[]") {


                        var name = eventArray.getJSONObject(e).getString("eventName")
                        var location = eventArray.getJSONObject(e).getString("location")
                        var price = eventArray.getJSONObject(e).getString("price")
                        var date = eventArray.getJSONObject(e).getString("date")

                        var event = Event(image, name, location, date, price)

                        if(nameToCheck == artistName){
                            events.add(event)

                        }

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

    private fun mLoad(string: String): Bitmap? {
        val url: URL = mStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()

        }
        return null
    }

    // Function to convert string to URL
    private fun mStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }


}

