package com.example.sample.Intefaces

import com.blood_bag.model.SignInModel

interface OnSignInView {
    fun onSignInData(signInModel: SignInModel?)
    fun onSignInStartLoading()
    fun onSignInStopLoading()
    fun onSignInShowMessage(errMsg: String?)
    fun onSignInTime(secMsg: String?)
}