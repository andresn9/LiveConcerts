package com.android.liveconcerts.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.objects.Event

class EventAdapter (private val eventList: ArrayList<Event>) :
RecyclerView.Adapter<EventAdapter.EventViewHolder>(){

    var onItemClick : ((Event) -> Unit)? = null

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
        holder.image.setImageResource(event.image)
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
}