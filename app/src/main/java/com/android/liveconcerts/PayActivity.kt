package com.android.liveconcerts

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.android.liveconcerts.databinding.ActivityEventDetailBinding
import com.android.liveconcerts.databinding.ActivityLoginBinding
import com.android.liveconcerts.objects.Artist
import com.android.liveconcerts.objects.Event
import com.android.liveconcerts.objects.Ticket
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class PayActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEventDetailBinding.inflate(layoutInflater) }
    private lateinit var db : FirebaseFirestore
    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())
    var mImage: Bitmap? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
            insertData(ticket)

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