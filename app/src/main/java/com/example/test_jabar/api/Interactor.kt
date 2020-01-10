package com.example.test_jabar.api

import com.example.test_jabar.data.response.CurrentWeatherResponse
import com.example.test_jabar.data.response.Forecast5Response
import com.example.test_jabar.helper.Constant
import io.reactivex.Observable


class Interactor(private val mService: ApiService) {

    fun getForecast5(zip:String): Observable<Forecast5Response> {
        return mService.getForecast5(zip, Constant.APP_ID_OPENWEATHER, Constant.UNIT_CELCIUS)
    }

    fun getWeatherNow(zip:String):Observable<CurrentWeatherResponse>{
        return mService.getWeatherNow(zip, Constant.APP_ID_OPENWEATHER, Constant.UNIT_CELCIUS)
    }
}