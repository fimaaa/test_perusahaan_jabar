package com.example.test_jabar.helper

import android.annotation.SuppressLint
import com.bumptech.glide.request.RequestOptions
import com.example.test_jabar.R
import java.text.SimpleDateFormat
import java.util.*


class Util {
    companion object{
        @SuppressLint("CheckResult")
        fun getRequestOption(): RequestOptions {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.color.light_transparent)
            requestOptions.error(R.color.black)

            return requestOptions
        }

        fun getDayTime():String{
            val c: Calendar = Calendar.getInstance()
            return when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    Constant.DAYTIME_PAGI
                }
                in 12..13 -> {
                    Constant.DAYTIME_SIANG
                }
                in 14..18 -> {
                    Constant.DAYTIME_SORE
                }
                in 19..24 -> {
                    Constant.DAYTIME_MALAM
                }
                else -> {
                    Constant.DAYTIME_UNKNOWN
                }
            }
        }

        fun dateToDay(dateString:String?):String{
            //2020-01-15 06:00:00
            val sourceFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val resultFormat =
                SimpleDateFormat("EEE", Locale.getDefault())
            if(dateString == null){
                return "-"
            }
            return try {
                resultFormat.format(sourceFormat.parse(dateString)?:"")
            } catch (e: Exception) {
                e.printStackTrace()
                "-"
            }
        }

        fun dateToTIME(dateString:String?):String{
            val sourceFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val resultFormat =
                SimpleDateFormat("hh:mm aaa", Locale.getDefault())
            if(dateString == null){
                return "-"
            }
            return try {
                resultFormat.format(sourceFormat.parse(dateString)?:"")
            } catch (e: Exception) {
                e.printStackTrace()
                "-"
            }
        }
    }
}