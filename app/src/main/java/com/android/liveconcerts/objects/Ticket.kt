package com.android.liveconcerts.objects

import android.os.Parcel
import android.os.Parcelable

class Ticket(var name: String? ="", var date: String? = "", var price: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "Ticket(name='$name', date='$date', price=$price)"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        if (p0 != null) {
            p0.writeString(name)
            p0.writeString(date)
            p0.writeString(price)
        }

    }

    companion object CREATOR : Parcelable.Creator<Ticket> {
        override fun createFromParcel(parcel: Parcel): Ticket {
            return Ticket(parcel)
        }

        override fun newArray(size: Int): Array<Ticket?> {
            return arrayOfNulls(size)
        }
    }

}