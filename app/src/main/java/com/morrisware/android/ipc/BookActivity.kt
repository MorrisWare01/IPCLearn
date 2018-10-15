package com.morrisware.android.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.ipc.model.Book
import kotlinx.android.synthetic.main.book_activity.*

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
class BookActivity : AppCompatActivity() {

    var mService: IBookManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_activity)
        button3.setOnClickListener {
            mService?.addBook(Book(1, editText.text.toString()))
        }
        button4.setOnClickListener {
            Log.d("BookActivity", "books:${mService?.books}")
        }

        bindService(Intent().setComponent(ComponentName("com.morrisware.android.ipc",
            "com.morrisware.android.ipc.service.BookService")
        ), conn, Context.BIND_AUTO_CREATE)
        Log.d("BookActivity", "onCreate ${Thread.currentThread()}")
    }

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("BookActivity", "connected:${service.toString()}")
            mService = IBookManager.Stub.asInterface(service)
            mService?.apply {
                Log.d("BookActivity", "books:$books")
                Log.d("BookActivity", "connected: ${onNewBookAddListener}")
                this.registerOnNewBookAddListener(onNewBookAddListener)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
        }
    }

    private val onNewBookAddListener = object : IOnNewBookAddListener.Stub() {
        override fun onNewBookAdd(book: Book?) {
            // TODO 当前线程是主线程，不是客户端binder线程？？？
            button3.text = "add Book"
            button4.text = "show Book"
            Log.d("BookActivity", "book add ${Thread.currentThread()}:${book}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mService?.asBinder()?.isBinderAlive == true) {
            mService?.unregisterOnNewBookAddListener(onNewBookAddListener)
        }
        unbindService(conn)
    }

}