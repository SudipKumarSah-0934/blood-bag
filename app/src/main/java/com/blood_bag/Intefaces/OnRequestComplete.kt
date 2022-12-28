package com.example.sample.Intefaces

interface OnRequestComplete {
    fun onRequestSuccess(obj: Any?)
    fun onRequestError(errMsg: String?)
}