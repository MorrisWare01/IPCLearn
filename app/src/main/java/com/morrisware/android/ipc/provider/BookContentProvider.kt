package com.morrisware.android.ipc.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * Created by MorrisWare on 2018/10/15.
 * Email: MorrisWare01@gmail.com
 */
class BookContentProvider : ContentProvider() {

    companion object {
        const val TAG = "BookContentProvider"
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "${Thread.currentThread()}")
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "${Thread.currentThread()}")
        return null
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return -1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return -1
    }

    override fun getType(uri: Uri): String? {
        Log.d(TAG, "${Thread.currentThread()}")
        return "*/*"
    }


}