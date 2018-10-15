package com.morrisware.android.ipc.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
data class Book(
    val id: Int,
    val name: String
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {

        override fun createFromParcel(source: Parcel): Book {
            return Book(source)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls<Book>(size)
        }
    }

    private constructor(source: Parcel) : this(
        id = source.readInt(),
        name = source.readString()
    )
}