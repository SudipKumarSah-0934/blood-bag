package com.blood_bag.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.blood_bag.R
import com.blood_bag.databinding.FragmentSignInBinding
import com.blood_bag.utilities.CheckInternetConnection
import com.example.sample.Intefaces.OnSignInView
import com.example.sample.localDb.AppSessionManager
import com.blood_bag.model.SignInModel
import com.blood_bag.Presenter.SignInPresenter
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SignInFragment : Fragment(),OnSignInView {
    lateinit var binding: FragmentSignInBinding
    lateinit  var appSessionManager: AppSessionManager
    private lateinit var signInPresenter: SignInPresenter
    var signInMap = HashMap<String, Any>()
    var dialog: Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(
            inflater,
            R.layout.fragment_sign_in, container, false
        )
        appSessionManager = AppSessionManager(requireContext())
        signInPresenter = SignInPresenter(this)
        binding.btnLoginSubmit.setOnClickListener {
            Toasty.success(requireContext(),"checked",Toasty.LENGTH_SHORT)
           login()
        }
        binding.createNewAc.setOnClickListener {
            Toasty.success(requireContext(),"checked",Toasty.LENGTH_SHORT)
            showFragment(SignUpFragment.newInstance())
        }
        dialog = Dialog(requireContext(), R.style.TransparentProgressDialog)
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.custom_progress_layout)
        return binding.root
    }

    private fun login() {
        val phone: String = binding.etLoginPhone.text.toString()
        if (TextUtils.isEmpty(phone)) {
            binding.etLoginPhone.error = "phone num field is empty!"
           return
       }
        try {
            val handler = Handler()
            handler.postDelayed(Runnable {
                try {
                    if (CheckInternetConnection.isInternetAvailable(context)) {
                        signInMap["phone"] = phone
                        MainScope().launch(Dispatchers.Main) {
                            signInPresenter.signInDataResponse(signInMap)
                        }
                    } else {
                        context?.let {
                            Toasty.warning(
                                it,
                                "Internet is not available!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, 100) // Show the ad after 10 sc
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
            }
    }

    override fun onSignInData(signInModel: SignInModel?) {
       Toasty.warning(requireContext(),signInModel.toString(),Toasty.LENGTH_SHORT)
        if (signInModel != null) {
            Log.d("TAG00", "onSignInData: "+signInModel.status)
                if (signInModel != null) {
                    appSessionManager.createMerchantLoginSession(
                        signInModel.data.name.toString(),
                        signInModel.data.token,
                        signInModel.data.image
                    )
                }
        }

    }

    override fun onSignInStartLoading() {
        dialog?.show()
    }

    override fun onSignInStopLoading() {
        dialog?.hide()
    }

    override fun onSignInShowMessage(errMsg: String?) {
        if (errMsg != null) {
            Toasty.error(requireContext(),errMsg,Toasty.LENGTH_SHORT)
            showFragment(SignUpFragment.newInstance())
        }
    }

    override fun onSignInTime(secMsg: String?) {
    }
    private fun showFragment(fragment: Fragment?) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,  // enter
            R.anim.fade_out,  // exit
            R.anim.fade_in,  // popEnter
            R.anim.slide_out // popExit
        )
        fragmentTransaction.replace(R.id.fragment_container, fragment!!)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }
}