package com.example.paymob.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}

fun get4DaysAgo():List<String>{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var list = mutableListOf<String>()
    val calendar = Calendar.getInstance()
    for( i in -3..0){
        calendar.add(Calendar.DATE,i)
        list.add(dateFormat.format(calendar.time))
    }
    return list.reversed()
}