package com.morrisware.android.ipc.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.morrisware.android.ipc.model.Book
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by MorrisWare on 2018/10/22.
 * Email: MorrisWare01@gmail.com
 */
class BookMessengerService : Service() {

    companion object {
        const val GET_BOOKS_WHAT = 0
        const val ADD_BOOK_WHAT = 1

        const val KEY_RESULT = "result"
        const val KEY_BOOK = "book"
        const val KEY_BOOKS = "books"
    }

    private val books = CopyOnWriteArrayList<Book>()

    /**
     * message.obj在多进程中只用传递实现了Parcelable接口才有效，一般使用setData(Bundle)
     */
    private val handler: Handler = Handler(Handler.Callback { message ->
        Log.d("BookMessengerService", "message: $message replyTo: ${message.replyTo}")
        when (message.what) {
            GET_BOOKS_WHAT -> {
                message.replyTo?.send(Message.obtain().apply {
                    this.what = GET_BOOKS_WHAT
                    val data = Bundle()
                    data.putBoolean(KEY_RESULT, true)
                    data.putParcelableArrayList(KEY_BOOKS, ArrayList(books))
                    this.data = data
                })
            }
            ADD_BOOK_WHAT -> {
                val data = message.data
                data.classLoader = javaClass.classLoader
                val book = data.getParcelable<Book>(KEY_BOOK)
                book?.apply {
                    val res = books.add(book)
                    message.replyTo?.send(Message.obtain().apply {
                        this.what = ADD_BOOK_WHAT
                        this.data = Bundle().apply {
                            putBoolean(KEY_RESULT, res)
                        }
                    })
                }
            }
        }
        true
    })

    override fun onBind(intent: Intent?): IBinder? {
        return Messenger(handler).binder
    }

}