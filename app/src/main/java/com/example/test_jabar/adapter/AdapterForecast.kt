package com.example.test_jabar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_jabar.R
import com.example.test_jabar.data.model.Forecast
import com.example.test_jabar.helper.Util
import kotlinx.android.synthetic.main.item_forecast.view.*

class AdapterForecast(private val mContext: Context, private val listForecast:MutableList<Forecast?>): RecyclerView.Adapter<AdapterForecast.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listForecast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listForecast[position]
        holder.itemView.tv_day_forecast.text = Util.dateToDay(data?.dtTxt)
        holder.itemView.tv_time_forecast.text = Util.dateToTIME(data?.dtTxt)
        val urlImage = mContext.getString(R.string.link_image_openweather,
            data?.weather?.get(0)?.icon ?:"04n")
        Glide.with(mContext)
            .applyDefaultRequestOptions(Util.getRequestOption())
            .load(urlImage)
            .into(holder.itemView.iv_weather_forecast)
        holder.itemView.tv_temperature_forecast.text = mContext.getString(R.string._12_Celsius,data?.main?.temp?.toInt())
    }
}