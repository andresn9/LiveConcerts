package com.android.liveconcerts.fragments

import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.databinding.ActivityLoginBinding
import com.android.liveconcerts.databinding.FragmentArtistBinding
import com.android.liveconcerts.objects.Artist
import com.android.liveconcerts.recycler.ArtistAdapter


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
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}