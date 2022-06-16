package com.android.liveconcerts.recycler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.objects.Event
import com.google.firebase.firestore.core.EventManager
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class EventAdapter (private val eventList: ArrayList<Event>) :
RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    var onItemClick : ((Event) -> Unit)? = null

    var mImage: Bitmap? = null

    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.event_image)
        val name : TextView = itemView.findViewById(R.id.event_title)
        val description : TextView = itemView.findViewById(R.id.event_description)
        val date : TextView = itemView.findViewById(R.id.event_date)
        val price : TextView = itemView.findViewById(R.id.event_price)

        init {
            itemView.setOnClickListener{
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "Nombre de evento: "+name.toString(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.event_result_view,parent, false)
        return EventViewHolder(v)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]

        var imageBit : Bitmap? = null



        myExecutor.execute {
            mImage = event.image?.let { mLoad(it) }
            myHandler.post {
                holder.image.setImageBitmap(mImage)

            }
        }






        holder.name.text = event.name
        holder.description.text = event.description
        holder.date.text = event.date
        holder.price.text = event.price.toString()

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(event)
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    // Function to establish connection and load image
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