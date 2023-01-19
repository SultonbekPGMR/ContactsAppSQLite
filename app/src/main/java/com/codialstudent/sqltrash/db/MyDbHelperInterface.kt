package com.codialstudent.sqltrash.db

import com.codialstudent.sqltrash.models.MyContact

interface MyDbHelperInterface {

    fun addContact(myContact: MyContact)
    fun getAllContacts():List<MyContact>
    fun deleteContact(myContact: MyContact)
    fun editContact(myContact: MyContact):Int
    fun clearDataBase()

}