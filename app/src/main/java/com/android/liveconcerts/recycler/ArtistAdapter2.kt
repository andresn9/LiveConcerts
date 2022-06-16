package com.android.liveconcerts.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.objects.Artist
import com.android.liveconcerts.objects.Artist2
import com.android.liveconcerts.objects.Ticket

class ArtistAdapter2(private val artistList: ArrayList<Artist2>)
    : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>(){



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistAdapter.ArtistViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ArtistViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}