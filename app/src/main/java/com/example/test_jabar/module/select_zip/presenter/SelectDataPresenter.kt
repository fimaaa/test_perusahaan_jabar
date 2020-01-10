package com.example.test_jabar.module.select_zip.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.test_jabar.helper.Constant
import com.example.test_jabar.module.select_zip.SelectDataInterface
import com.example.test_jabar.module.show_weather.view.ShowWeatherActivity


class SelectDataPresenter(private val mContext:Context, private val mView:SelectDataInterface.View):SelectDataInterface.Presenter {

    override fun goToZIP(name: String, zipCode: String) {
        mContext.startActivity(ShowWeatherActivity.startActivity(mContext,name,zipCode))
    }

    override fun goToLinkedIn() {
        val uriUrl: Uri = Uri.parse(Constant.LINKEDIN_URL_ME)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        mContext.startActivity(launchBrowser)
    }

}