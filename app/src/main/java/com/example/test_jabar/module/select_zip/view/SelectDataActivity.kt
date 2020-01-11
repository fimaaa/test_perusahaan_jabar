package com.example.test_jabar.module.select_zip.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            val username = et_username.editText!!.text.toString()
            val zipCode = et_zipcode.editText!!.text.toString()
            when {
                username.isEmpty() -> et_username.error = getString(R.string.et_mus_filled,"Username")
                zipCode.isEmpty()-> et_zipcode.error = getString(R.string.et_mus_filled,"Zipcode")
                else -> {
                    mPresenter.goToZIP(username,zipCode)
                }
            }
        }
    }
}
