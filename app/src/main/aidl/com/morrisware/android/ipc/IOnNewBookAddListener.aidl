package com.morrisware.android.ipc;

import com.morrisware.android.ipc.model.Book;

interface IOnNewBookAddListener {

    void onNewBookAdd(in Book book);

}
