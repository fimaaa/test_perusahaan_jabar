package com.example.test_jabar.module.show_weather.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.test_jabar.R
import com.example.test_jabar.adapter.AdapterForecast
import com.example.test_jabar.data.model.Coord
import com.example.test_jabar.data.model.Forecast
import com.example.test_jabar.helper.Constant
import com.example.test_jabar.helper.Util
import com.example.test_jabar.module.show_weather.ShowWeatherInterface
import com.example.test_jabar.module.show_weather.presenter.ShowWeatherPresenter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kaopiz.kprogresshud.KProgressHUD
import kotlinx.android.synthetic.main.activity_showweather.*
import kotlinx.android.synthetic.main.sheet_detailuser.view.*


class ShowWeatherActivity:AppCompatActivity(), ShowWeatherInterface.View ,
    SwipeRefreshLayout.OnRefreshListener {
    companion object{
        fun startActivity(mContext:Context, nameUser:String, codeZIP:String):Intent{
            val intent = Intent(mContext,ShowWeatherActivity::class.java)
            intent.putExtra(Constant.BUNDLE_NAMEUSER_TEXT,nameUser)
            intent.putExtra(Constant.BUNDLE_ZIPCODE_TEXT,codeZIP)

            return intent
        }
    }

    private val mPresenter = initPresenter()
    private lateinit var bottomSheetDetail: BottomSheetDialog
    private lateinit var view: View
    private lateinit var loading:KProgressHUD

    override fun initPresenter(): ShowWeatherInterface.Presenter {
        return ShowWeatherPresenter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showweather)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setBackgroundColor(getColor(android.R.color.transparent))
                .setDimAmount(0.7f)
        }else{
            @Suppress("DEPRECATION")
            loading = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setBackgroundColor(resources.getColor(android.R.color.transparent))
                .setDimAmount(0.7f)

        }
        loading.show()
        swipe_layout_showweather.setOnRefreshListener(this)
        mPresenter.onGetWeatherNow()
        mPresenter.onGetForecast5()
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun initBottomSheetDialog(weatherImage: String,weatherText:String,country:String, zipCode: String, city: String,coordinat:Coord?,detailWeather:String){
        view = layoutInflater.inflate(R.layout.sheet_detailuser, null)
        bottomSheetDetail = BottomSheetDialog(this)
        bottomSheetDetail.setContentView(view)
        bottomSheetDetail.setCanceledOnTouchOutside(true)
        bottomSheetDetail.setCancelable(true)
        Glide.with(this)
            .applyDefaultRequestOptions(Util.getRequestOption())
            .load(weatherImage)
//            .signature(ObjectKey())
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .skipMemoryCache(true)
            .into(view.iv_weather_detail)
        view.tv_weather_detail.text = weatherText
        view.tv_country_detail.text = country
        view.tv_city_detail.text = "$city($zipCode)"
        view.tv_coordinat_detail.text = coordinat.toString()
        view.tv_coordinat_detail.setOnClickListener {
            val urlString = "geo:${coordinat?.lat},${coordinat?.lon}"
            println("URLString $urlString")
            val gmmIntentUri = Uri.parse(urlString)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        view.tv_detailweather_detail.text = detailWeather
        btn_detailinfo_show.setOnClickListener {
            bottomSheetDetail.show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setWeatherNow(weatherImage: String, temperature:String, dayTime: String, userName: String,weatherText:String, zipCode:String, city: String){
        Glide.with(this)
            .applyDefaultRequestOptions(Util.getRequestOption())
            .load(weatherImage)
//            .signature(ObjectKey())
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .skipMemoryCache(true)
            .into(iv_weather_now)
        tv_temperature_now.text = temperature
        tv_greeting_show.text = getString(R.string.greeting_user,dayTime,userName)
        tv_location_show.text = getString(R.string.location_condition,zipCode,city)
        tv_weathercondition_show.text = getString(R.string.weather_condition,weatherText)
        swipe_layout_showweather.isRefreshing = false
        Handler().postDelayed({
            layout_showweather.visibility = View.VISIBLE
            loading.dismiss()
        }, Constant.DELAY_LOADING_VIEW)

    }

    override fun setForecast5(forecast: MutableList<Forecast?>?,message: String?) {
        if(forecast != null) {
            spin_kit_forecast.visibility = View.GONE
            val adapter = AdapterForecast(this, forecast)
            rcv_weather_5days.adapter = adapter
        }else{
            spin_kit_forecast.visibility = View.GONE
            rcv_weather_5days.adapter = null
            tv_errorforecast.text = message?:getString(R.string.something_wrong)
            btn_errorforecast.setOnClickListener {
                spin_kit_forecast.visibility = View.VISIBLE
                tv_errorforecast.visibility = View.VISIBLE
                btn_errorforecast.visibility = View.VISIBLE
                mPresenter.onGetForecast5()
            }
            tv_errorforecast.visibility = View.VISIBLE
            btn_errorforecast.visibility = View.VISIBLE
        }
    }

    override fun setViewFailed(message: String?) {
        swipe_layout_showweather.isRefreshing = false
        val showMessage = message ?: getString(R.string.something_wrong)
        Handler().postDelayed({
            Toast.makeText(this, showMessage, Toast.LENGTH_SHORT).show()
            loading.dismiss()
            finish()
        }, Constant.DELAY_LOADING_VIEW)
    }

    override fun onRefresh() {
//        Handler().postDelayed(Runnable {
//            mPresenter.onGetWeatherNow()
//        }, 2000)
        spin_kit_forecast.visibility = View.VISIBLE
        mPresenter.onGetWeatherNow()
        mPresenter.onGetForecast5()
    }
}