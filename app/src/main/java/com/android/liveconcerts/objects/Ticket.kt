package com.android.liveconcerts.objects

class Ticket(var name: String? ="", var date: String? = "", var price: Int?) {
    override fun toString(): String {
        return "Ticket(name='$name', date='$date', price=$price)"
    }
}