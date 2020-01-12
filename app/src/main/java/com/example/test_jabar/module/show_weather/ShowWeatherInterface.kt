package com.example.test_jabar.module.show_weather

import com.example.test_cermati.base.BasePresenter
import com.example.test_cermati.base.BaseView
import com.example.test_jabar.data.model.Coord
import com.example.test_jabar.data.model.Forecast

interface ShowWeatherInterface {
    interface View: BaseView<Presenter>{

        fun setWeatherNow(
            weatherImage: String,
            temperature:String,
            dayTime: String,
            userName: String,
            weatherText:String,
            zipCode:String,
            city: String
        )
        fun initBottomSheetDialog(
            weatherImage: String,
            weatherText:String,
            country:String,
            zipCode: String,
            city: String,
            coordinat:Coord?,
            detailWeather:String,
            maxTemperature:String,
            minTemperature:String,
            windSpeed:String?,
            cloudness:String?,
            pressure:String?,
            rainPercentage:String?
        )
        fun setForecast5(forecast:MutableList<Forecast?>?,message: String?)

        fun setViewFailed(message:String?)

    }
    interface Presenter: BasePresenter<View>{
        fun onGetWeatherNow()

        fun onGetForecast5()
    }
}