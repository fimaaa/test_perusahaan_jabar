package com.example.test_jabar.module.show_weather.presenter

import android.app.Activity
import com.example.test_jabar.R
import com.example.test_jabar.api.ApiServiceBuilder
import com.example.test_jabar.api.Interactor
import com.example.test_jabar.data.response.CurrentWeatherResponse
import com.example.test_jabar.data.response.ErrorResponse
import com.example.test_jabar.data.response.Forecast5Response
import com.example.test_jabar.helper.Constant
import com.example.test_jabar.helper.Util
import com.example.test_jabar.module.show_weather.ShowWeatherInterface
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException


class ShowWeatherPresenter(private val mContext:Activity, private val mView:ShowWeatherInterface.View):ShowWeatherInterface.Presenter {
    private val compositeDisposable = CompositeDisposable()
    private val mInteractor = Interactor(ApiServiceBuilder().provideApiService())

    override fun onGetWeatherNow() {
        val zipCode = mContext.intent?.extras?.getString(Constant.BUNDLE_ZIPCODE_TEXT)?:"Null Data"
        val userName = mContext.intent?.extras?.getString(Constant.BUNDLE_NAMEUSER_TEXT)?:"Null Data"
        compositeDisposable.add(mInteractor.getWeatherNow(
            zipCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<CurrentWeatherResponse>() {
                override fun onNext(responseServer: CurrentWeatherResponse) {
                    val city = responseServer.name
                    val weatherNow = responseServer.listWeather
                    if (city != null && weatherNow != null) {
                        val dayTime = Util.getDayTime()
                        val weatherText = weatherNow[0].main?:"Unknown"
                        val countryName = responseServer.sys?.country?:"Unknown"
                        val cityName = responseServer.name
                        val urlImage = mContext.getString(R.string.link_image_openweather,
                            responseServer.listWeather[0].icon ?:"04n")
                        val temperature = mContext.getString(R.string._12_Celsius,responseServer.main?.temp?.toInt())
                        val coordinat = responseServer.coord
                        val detailWeather = weatherNow[0].description?:"Unknown"
                        val maxTemperature = mContext.getString(R.string._12_Celsius,responseServer.main?.tempMax?.toInt())
                        val minTemperature = mContext.getString(R.string._12_Celsius,responseServer.main?.tempMin?.toInt())
                        val windSpeed = mContext.getString(R.string.wind_speed,responseServer.wind?.speed?.toInt())
                        val cloudness = mContext.getString(R.string.cloudiness_percentage,responseServer.clouds?.all)
                        val pressure = mContext.getString(R.string.pressure_variabel,responseServer.main?.pressure?.toInt())
                        val rainPercentage = mContext.getString(R.string.rain_percentage,responseServer.rain?.jsonMember3h?.toInt())

                        mView.setWeatherNow(
                            urlImage,
                            temperature,
                            dayTime,
                            userName,
                            weatherText,
                            zipCode,
                            city
                        )
                        mView.initBottomSheetDialog(
                            urlImage,
                            weatherText,
                            countryName,
                            zipCode,
                            cityName,
                            coordinat,
                            detailWeather,
                            maxTemperature,
                            minTemperature,
                            windSpeed,
                            cloudness,
                            pressure,
                            rainPercentage
                        )
                    }
                }
                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        val body =
                            e.response()!!.errorBody()
                        val gson = Gson()
                        val adapter: TypeAdapter<ErrorResponse> =
                            gson.getAdapter(ErrorResponse::class.java)
                        try {
                            val errorParser: ErrorResponse = adapter.fromJson(body!!.string())
                            mView.setViewFailed(errorParser.message)
                        } catch (e: IOException) {
                            mView.setViewFailed(e.message)
                            e.printStackTrace()
                        }
                    }else{
                        mView.setViewFailed(e.message)
                    }
                }

                override fun onComplete() {
                }
            }))
    }

    override fun onGetForecast5() {
        val zipCode = mContext.intent?.extras?.getString(Constant.BUNDLE_ZIPCODE_TEXT)?:"Null Data"
        compositeDisposable.add(mInteractor.getForecast5(
            zipCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Forecast5Response>() {
                override fun onNext(responseServer: Forecast5Response) {
                    mView.setForecast5(responseServer.list,null)
                }
                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        val body =
                            e.response()!!.errorBody()
                        val gson = Gson()
                        val adapter: TypeAdapter<ErrorResponse> =
                            gson.getAdapter(ErrorResponse::class.java)
                        try {
                            val errorParser: ErrorResponse = adapter.fromJson(body!!.string())
                            mView.setForecast5(null,errorParser.message)
                        } catch (e: IOException) {
                            e.printStackTrace()
                            mView.setForecast5(null,e.message)
                        }
                    }else{
                        mView.setForecast5(null,e.message)
                    }
                }

                override fun onComplete() {
                }
            }))
    }
}