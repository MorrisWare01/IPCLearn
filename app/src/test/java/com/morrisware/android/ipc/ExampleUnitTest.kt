package com.morrisware.android.ipc

import com.morrisware.android.ipc.model.User
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun serializableTest() {
        val obs = ObjectOutputStream(FileOutputStream("test.txt"))
        obs.writeObject(User(1, "ware"))
        obs.close()
    }

    @Test
    fun testReadSerializable() {
        val bis = ObjectInputStream(FileInputStream("test.txt"))
        val user = bis.readObject() as User
        bis.close()
        System.out.println(user.toString())
    }

    @Test
    fun testParcelable() {



    }
}