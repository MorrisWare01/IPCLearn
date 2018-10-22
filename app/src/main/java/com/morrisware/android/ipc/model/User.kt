package com.morrisware.android.ipc.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
data class User(
    val id: Int,
    val name: String,
    val age: Int = 0,
    val child: User? = null
) : Serializable, Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeInt(age)
        dest.writeParcelable(child, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        const val serialVersionUID = 1L

        @JvmField
        val CREATOR = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User {
                val id = source.readInt()
                val name = source.readString() ?: ""
                val age = source.readInt()
                val child = source.readParcelable<User?>(User::class.java.classLoader)
                return User(id, name, age, child)
            }

            override fun newArray(size: Int): Array<User?> {
                return arrayOfNulls(size)
            }
        }
    }


}