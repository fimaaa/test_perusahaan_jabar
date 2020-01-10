package com.example.test_jabar.module.select_zip.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test_jabar.R
import com.example.test_jabar.module.select_zip.SelectDataInterface
import com.example.test_jabar.module.select_zip.presenter.SelectDataPresenter
import kotlinx.android.synthetic.main.activity_selectdata.*

class SelectDataActivity : AppCompatActivity(), SelectDataInterface.View {

    private val mPresenter = initPresenter()
    override fun initPresenter(): SelectDataInterface.Presenter {
        return SelectDataPresenter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectdata)

        tv_copyright.setOnClickListener {
            mPresenter.goToLinkedIn()
        }

        btn_inputdata.setOnClickListener {
            val username = et_username.text.toString()
            val zipCode = et_zipcode.text.toString()
            mPresenter.goToZIP(username,zipCode)
        }
    }
}
