package com.morrisware.android.ipc.service

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Log
import com.morrisware.android.ipc.IBookManager
import com.morrisware.android.ipc.IOnNewBookAddListener
import com.morrisware.android.ipc.model.Book
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
class BookService : Service() {

    companion object {
        private const val PERMISSION = "com.morrisware.permission.ACCESS_BOOK_SERVICE"
    }

    private val books = CopyOnWriteArrayList<Book>()
    private val listeners = RemoteCallbackList<IOnNewBookAddListener>()

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("BookService", "onBind:${mBinder.toString()}")
        return mBinder.asBinder()
    }

    override fun onCreate() {
        super.onCreate()
        books.add(Book(1, "A"))
    }

    private val mBinder = object : IBookManager.Stub() {

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            val check = checkCallingOrSelfPermission(PERMISSION)
            if (check == PackageManager.PERMISSION_DENIED) {
                return false
            }

            val packages = packageManager.getPackagesForUid(getCallingUid())
            val packageName = packages?.get(0)
            if (packageName != null && !packageName.startsWith("com.morrisware")) {
                return false
            }
            return super.onTransact(code, data, reply, flags)
        }

        override fun registerOnNewBookAddListener(listener: IOnNewBookAddListener?) {
            listener?.apply {
                Log.d("BookService", "register: ${Thread.currentThread()} $listener,binder:${listener.asBinder()}")
                this@BookService.listeners.register(listener)
            }
        }

        override fun unregisterOnNewBookAddListener(listener: IOnNewBookAddListener?) {
            listener?.apply {
                Log.d("BookService", "unregister: $listener,binder:${listener.asBinder()}")
                this@BookService.listeners.unregister(listener)
            }
            Log.d("BookService", "listener count: ${this@BookService.listeners.registeredCallbackCount}")
        }

        @Throws(RemoteException::class)
        override fun getBookMap(): MutableMap<Any?, Any?> {
            return ConcurrentHashMap<Int, Book>().toMutableMap()
        }

        @Throws(RemoteException::class)
        override fun getBooks(): MutableList<Book> {
            return this@BookService.books
        }

        @Throws(RemoteException::class)
        override fun addBook(book: Book?) {
            if (book != null) {
                this@BookService.books.add(book)
                notifyBookAdd(book)
            }
        }

        private fun notifyBookAdd(book: Book) {
            val count = this@BookService.listeners.beginBroadcast()
            for (i in 0 until count) {
                val listener = this@BookService.listeners.getBroadcastItem(i)
                try {
                    listener?.onNewBookAdd(book)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            this@BookService.listeners.finishBroadcast()
        }
    }

}