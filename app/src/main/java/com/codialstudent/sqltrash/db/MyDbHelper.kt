package com.codialstudent.sqltrash.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.codialstudent.sqltrash.models.MyContact

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, "MyDbContact", null, 1),
    MyDbHelperInterface {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query =
            "create table MyContacts (id integer not null primary key autoincrement unique, name text not null, number text not null)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addContact(myContact: MyContact) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", myContact.name)
        contentValues.put("number", myContact.number)
        database.insert("MyContacts", null, contentValues)
        database.close()
    }

    override fun getAllContacts(): List<MyContact> {
        val database = this.readableDatabase
        val query = "select * from MyContacts"
        val cursor = database.rawQuery(query, null)
        val list = ArrayList<MyContact>()
        if (cursor.moveToFirst()) {
            do {
                val myContact = MyContact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                list.add(myContact)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun deleteContact(myContact: MyContact) {
        val database = this.writableDatabase
        database.delete("MyContacts", "id=?", arrayOf(myContact.id.toString()))
        database.close()
    }

    override fun editContact(myContact: MyContact): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", myContact.id)
        contentValues.put("name", myContact.name)
        contentValues.put("number", myContact.number)

        return database.update(
            "MyContacts",
            contentValues,
            "id=?",
            arrayOf(myContact.id.toString())
        )
    }

    override fun clearDataBase() {
        val database = this.writableDatabase
        database.execSQL("delete from " + "MyContacts")
    }
}