package com.example.test_jabar.module.select_zip

import com.example.test_cermati.base.BasePresenter
import com.example.test_cermati.base.BaseView

interface SelectDataInterface {
    interface View:BaseView<Presenter>
    interface Presenter:BasePresenter<View>{
        fun goToZIP(name:String,zipCode:String)

        fun goToLinkedIn()
    }
}