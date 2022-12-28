package com.blood_bag.Presenter

import com.blood_bag.view.fragments.SignInFragment
import com.example.sample.interfaces.OnRequestComplete
import com.blood_bag.InvokedApi.InvokeSignInApi
import com.blood_bag.model.SignInModel
import java.util.HashMap

class SignInPresenter(private val onSignInView: SignInFragment) {
     fun signInDataResponse(signInMap: HashMap<String, Any>?) {
        onSignInView.onSignInStartLoading()
        InvokeSignInApi(signInMap, object : OnRequestComplete {
            override fun onRequestSuccess(obj: Any?) {
                onSignInView.onSignInData(obj as SignInModel?)
                onSignInView.onSignInStopLoading()
            }

            override fun onRequestError(errMsg: String?) {
//                if (errMsg!!.length > 3) { //just checks that there is something. You may want to check that length is greater than or equal to 3
//                    val bar = errMsg.substring(0, 3)
//                    if (bar == "666") {
//                        onSignInView.onSignInTime(errMsg)
//                    } else {
//                        onSignInView.onSignInShowMessage(errMsg)
//                    }
//                    //do what you need with it
//                }
                onSignInView.onSignInShowMessage(errMsg)
                onSignInView.onSignInStopLoading()
            }
        })
    }
}