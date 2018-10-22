package com.morrisware.android.ipc;

import com.morrisware.android.ipc.model.Book;
import com.morrisware.android.ipc.IOnNewBookAddListener;

interface IBookManager {

    List<Book> getBooks();

    void addBook(in Book book);

    Map getBookMap();

    void registerOnNewBookAddListener(in IOnNewBookAddListener listener);

    void unregisterOnNewBookAddListener(in IOnNewBookAddListener listener);

    void testIn(in List<Book> books);

    void testOut(out List<Book> books);

    void testInout(inout List<Book> books);

}
