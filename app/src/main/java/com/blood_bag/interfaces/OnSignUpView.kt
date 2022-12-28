package com.example.sample.interfaces


import com.blood_bag.model.SignUpModel

interface OnSignUpView {
    fun onRegisterData(signUpModel: SignUpModel)
    fun onRegisterStartLoading()
    fun onRegisterStopLoading()
    fun onRegisterShowMessage(errMsg: String?)
    fun onRegisterTime(secMsg: String?)
}