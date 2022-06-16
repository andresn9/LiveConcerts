package com.android.liveconcerts

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.android.liveconcerts.ui.main.SectionsPagerAdapter
import com.android.liveconcerts.databinding.ActivityMainBinding
import com.android.liveconcerts.objects.Artist
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var artists = arrayListOf<Artist>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        readJason()


        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter


        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab






        fab.setOnClickListener { view ->
            var intent = Intent (this, TicketActivity::class.java)
            startActivity(intent)
        }



    }

    fun readJason(){

        var json :String? = null
        val jsonArr : JSONArray? = null
        try {
            val inputStream : InputStream = assets.open("data.json")
            json = inputStream.bufferedReader().use {it.readText()}
            val jsonArr = JSONArray(json)


            for (i in 0..jsonArr.length()-1){
                var jsonObj = jsonArr.getJSONObject(i)

                var artist = Artist(jsonObj.getString("image"),jsonObj.getString("name"))
                artists.add(artist)

            }



        }
        catch (e : IOException){

        }



    }
}