package com.codialstudent.sqltrash.models

import android.icu.text.Transliterator.Position

data class LiveDataType(
    val state: Boolean,
    val position: Int,
    val myContact: MyContact
)