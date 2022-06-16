package com.android.liveconcerts.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.ArtistActivity
import com.android.liveconcerts.EventDetailActivity
import com.android.liveconcerts.R
import com.android.liveconcerts.databinding.FragmentArtistBinding
import com.android.liveconcerts.objects.Artist
import com.android.liveconcerts.recycler.ArtistAdapter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.net.URL


/**
 * A simple [Fragment] subclass.
 * Use the [ArtistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private lateinit var artistList : ArrayList<Artist>
    private lateinit var artistAdapter: ArtistAdapter

    private lateinit var artistsNames : ArrayList<String>
    private lateinit var artistsImages : ArrayList<String>

    var artists = arrayListOf<Artist>()






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

//        val bundle = arguments
//        val message = bundle!!.getString("mText")

        // Inflate the layout for this fragment

        readJson()

        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        artistList = ArrayList()

//        artistList.add(Artist(R.drawable.code_android_logo , "Los chichos"))
//        artistList.add(Artist(R.drawable.code_android_logo , "Los pecos"))
//        artistList.add(Artist(R.drawable.code_android_logo , "Los juncos"))
//        artistList.add(Artist(R.drawable.code_android_logo , "Las ketchu"))
//        artistList.add(Artist(R.drawable.code_android_logo , "Los manolos"))

        artistAdapter = ArtistAdapter(artists)
        recyclerView.adapter = artistAdapter

        artistAdapter.onItemClick = {
            val intent = Intent(activity, ArtistActivity::class.java)
            intent.putExtra("artistaImage", it.image)
            intent.putExtra("artistaName", it.name)
            startActivity(intent)
        }



        return view
    }
    public fun readJson() {

        var json: String? = null
        val jsonArr: JSONArray? = null
        try {
            val inputStream: InputStream = activity?.assets!!.open("data.json")
            json = inputStream.bufferedReader().use { it.readText() }
            val jsonArr = JSONArray(json)



            for (i in 0..jsonArr.length() - 1) {
                var jsonObj = jsonArr.getJSONObject(i)


                var image = jsonObj.getString("image")
                var imageURL = URL(jsonObj.getString("image"))
                var name = jsonObj.getString("name")


                val resultImage: Deferred<Bitmap?> = lifecycleScope.async(Dispatchers.IO) {
                    imageURL.toBitmap
                }

                var artist = Artist(image, name)

                artists.add(artist)

            }




        } catch (e: IOException) {

        }


    }
    val URL.toBitmap:Bitmap?
        get() {
            return try {
                BitmapFactory.decodeStream(openStream())
            }catch (e: IOException){null}
        }


}