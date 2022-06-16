package com.android.liveconcerts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.objects.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class EventDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEventDetailBinding.inflate(layoutInflater) }
    private lateinit var db : FirebaseFirestore
    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())
    var mImage: Bitmap? = null

    companion object {
        val INTENT_PARCELABLE = "ticket"
    }

    private fun goToUrl() {
        val uriUrl: Uri = Uri.parse("https://www.ticketmaster.es/")
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnWeb.setOnClickListener {
            goToUrl()
        }

        val event = intent.getParcelableExtra<Event>("event")
        if (event !=null){


            myExecutor.execute {
                mImage = event.image?.let { mLoad(it) }
                myHandler.post {
                    binding.artistImage.setImageBitmap(mImage)

                }
            }

            val text = binding.artistName
            val price = binding.eventPrice



            db = FirebaseFirestore.getInstance()

            text.text = event.name
            price.text = event.price
            //image.setImageResource(image.sur)
        }

        binding.btnPaypal.setOnClickListener{
            var ticket = Ticket(event?.name, event?.date, event?.price.toString())


            val intent = Intent(this, PayActivity::class.java)
            intent.putExtra(INTENT_PARCELABLE, ticket)
            startActivity(intent)

        }





    }

//    private fun showPaypal(){
//        val paypalIntent = Intent(this, PayPalActivity::class.java)
//        startActivity(paypalIntent)
//    }

    private fun insertData(ticket: Ticket){
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid


        db.collection("userData").document(currentuser).collection("tickets").document(ticket.name.toString())
            .set(ticket).addOnSuccessListener {
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