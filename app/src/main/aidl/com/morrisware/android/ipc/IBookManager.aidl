package com.morrisware.android.ipc;

import com.morrisware.android.ipc.model.Book;
import com.morrisware.android.ipc.IOnNewBookAddListener;

interface IBookManager {

    List<Book> getBooks();

    void addBook(in Book book);

    Map getBookMap();

    void registerOnNewBookAddListener(IOnNewBookAddListener listener);

    void unregisterOnNewBookAddListener(IOnNewBookAddListener listener);
}
