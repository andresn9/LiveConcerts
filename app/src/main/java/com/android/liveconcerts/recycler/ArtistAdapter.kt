package com.android.liveconcerts.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.objects.Artist

class ArtistAdapter(private val artistList: ArrayList<Artist>)
    : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>(){

    var onItemClick : ((Artist) -> Unit)? = null

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.artist_picture)
        val text : TextView = itemView.findViewById(R.id.artist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_result_view, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artistList[position]
        holder.image.setImageURI(artist.image.toUri())
        holder.text.text = artist.name

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(artist)
        }
    }

    override fun getItemCount(): Int {
        return artistList.size
    }
}