package com.example.paymob.utils

import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun get4DaysAgo():String{
    val dateFormat = SimpleDateFormat("YYYY-MM-dd")
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE,-4)
    val result = dateFormat.format(calendar.time)
    return result
}

fun get4DaysAgoText():String{
    val dateFormat = SimpleDateFormat("dd-MMMM-yyyy")
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE,-4)
    val result = dateFormat.format(calendar.time)
    return result
}

// Extension function for Double
fun Double.prettyString(): String {
    val decimalFormat = DecimalFormat("0.00")
    return decimalFormat.format(this)
}

// Extension function for Float
fun Double.removeDecimalIfInteger(): String {
    return if (this == this.toInt().toDouble()) {
        this.toInt().toString() // Remove decimal if it's an integer
    } else {
        this.prettyString() // Keep decimal if it's not an integer
    }
}