package com.android.liveconcerts.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.liveconcerts.R
import com.android.liveconcerts.objects.Ticket

class TicketAdapter (private val ticketList: ArrayList<Ticket>)
    : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    var onItemClick : ((Ticket) -> Unit)? = null

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title : TextView = itemView.findViewById(R.id.ticket_title)
        val date : TextView = itemView.findViewById(R.id.ticket_date)
        val price : TextView = itemView.findViewById(R.id.ticket_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ticket_view,parent, false)
        return TicketViewHolder(v)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.title.text = ticket.name
        holder.date.text = ticket.date
        holder.price.text = ticket.price.toString()

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(ticket)
        }
    }

    override fun getItemCount(): Int {
       return ticketList.size
    }
}