package com.morrisware.android.ipc.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.morrisware.android.ipc.R
import com.morrisware.android.ipc.model.Book
import kotlinx.android.synthetic.main.book_activity.*

/**
 * Created by MorrisWare on 2018/10/22.
 * Email: MorrisWare01@gmail.com
 */
class BookMessengerActivity : AppCompatActivity() {

    private lateinit var bookService: Messenger
    private var isBound = false

    private val bookMessenger = Messenger(Handler(Handler.Callback {
        when (it.what) {
            BOOK_SERVICE_GET_BOOKS_WHAT -> {
                val data = it.data
                data.classLoader = javaClass.classLoader
                val res = data.getBoolean(BOOK_SERVICE_KEY_RESULT)
                val books = data.getParcelableArrayList<Book>(BOOK_SERVICE_KEY_BOOKS)
                Log.d("BookMessengerActivity", "getBooks res: $res books: $books")
            }
            BOOK_SERVICE_ADD_BOOK_WHAT -> {
                val data = it.data
                data.classLoader = javaClass.classLoader
                val res = data.getBoolean(BOOK_SERVICE_KEY_RESULT)
                Log.d("BookMessengerActivity", "addBook res: $res")
            }
        }
        true
    }))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_activity)
        button3.setOnClickListener {
            if (this::bookService.isInitialized) {
                bookService.send(Message.obtain().apply {
                    this.what = BOOK_SERVICE_ADD_BOOK_WHAT
                    this.replyTo = bookMessenger
                    this.data = Bundle().apply {
                        this.putParcelable(BOOK_SERVICE_KEY_BOOK, Book(1, editText.text.toString()))
                    }
                })
            }
        }
        button4.setOnClickListener {
            if (this::bookService.isInitialized) {
                bookService.send(Message.obtain().apply {
                    this.what = BOOK_SERVICE_GET_BOOKS_WHAT
                    this.replyTo = bookMessenger
                })
            }
        }

        val intent = Intent()
        intent.component = ComponentName("com.morrisware.android.ipc",
            "com.morrisware.android.ipc.messenger.BookMessengerService")
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(conn)
        }
    }

    private val conn = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            bookService = Messenger(service)
        }
    }

    companion object {
        const val BOOK_SERVICE_GET_BOOKS_WHAT = 0
        const val BOOK_SERVICE_ADD_BOOK_WHAT = 1

        const val BOOK_SERVICE_KEY_RESULT = "result"
        const val BOOK_SERVICE_KEY_BOOK = "book"
        const val BOOK_SERVICE_KEY_BOOKS = "books"
    }


}