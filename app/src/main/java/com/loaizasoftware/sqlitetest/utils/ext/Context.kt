package com.loaizasoftware.sqlitetest.utils.ext

import android.content.Context
import android.widget.Toast


infix fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}