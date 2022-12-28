package com.blood_bag.view.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.blood_bag.Presenter.SignUpPresenter
import com.blood_bag.R
import com.blood_bag.databinding.FragmentSignUpBinding
import com.blood_bag.model.SignUpModel
import com.blood_bag.utilities.CheckInternetConnection
import com.example.sample.localDb.AppSessionManager
import info.hoang8f.android.segmented.SegmentedGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.blood_bag.utilities.CommonDateUtilities
import com.blood_bag.utilities.CommonUtils
import com.example.sample.interfaces.OnSignUpView
import java.text.SimpleDateFormat
import java.util.*



class SignUpFragment : Fragment(),OnSignUpView {
    private lateinit var binding:FragmentSignUpBinding
    private var date: DatePickerDialog.OnDateSetListener? = null
    private lateinit var myCalendar: Calendar
    private lateinit var commonDateUtilities: CommonDateUtilities
    private lateinit var dialog: Dialog
    var appSessionManager: AppSessionManager? = null
    lateinit var presenter: SignUpPresenter
    lateinit var cUtils: CommonUtils
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private var isShowPass = false
    var registerMap = java.util.HashMap<String, Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        presenter= SignUpPresenter(this)
        appSessionManager = AppSessionManager(requireContext())
        cUtils = CommonUtils(requireContext())

        dialog = Dialog(requireContext(), R.style.TransparentProgressDialog)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_progress_layout)
        commonDateUtilities = CommonDateUtilities(requireContext())


        appSessionManager?.storeRegInfo("")
        initCalender()
        binding.dob.setOnClickListener(View.OnClickListener {
            dobStatus()
        })
        binding.lastBloodDonation.setOnClickListener(View.OnClickListener {
            dobStatus()
        })

        binding.createNewAc.setOnClickListener(View.OnClickListener {
            verifyData()
        })



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
            }
    }
    private fun verifyData() {
       /* if (TextUtils.isEmpty(binding.firstName.text)) {
            binding.firstName.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.lastName.text)) {
            binding.lastName.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.etEmail.text)) {
            binding.etEmail.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.etPassword.text)) {
            binding.etPassword.error = "Field is empty!"
            return
        }*/
        if (TextUtils.isEmpty(binding.Name.text)) {
            binding.Name.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.phoneNumber.text)) {
            binding.phoneNumber.error = "Field is empty!"
            return
        }
        if (TextUtils.isEmpty(binding.dobTv.text)) {
            binding.dobTv.error = "Field is empty!"
            return
        }
        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId) as RadioButton
            val gender = radioButton.text.toString()
            Log.d("TAG", "onCheckedChanged: $gender")
            registerMap["gender"] = gender
        }
        val dob: String = binding.dobTv.text.toString()
        if (TextUtils.isEmpty(dob) || dob == getString(R.string.dob)) {
            binding.dobTv.error = ("Date Of Birth Required!")
            return
        }


        if (commonDateUtilities.getYearDifference(dob) < 18) {
            binding.dobTv.error = ("Must be at least 18 years old")
            binding.dobTv.requestFocus()
            Toast.makeText(requireContext(), "Must be at least 18 years old", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val dolbd: String = binding.lastBloodDonationTv.text.toString()
        if (TextUtils.isEmpty(dolbd) || dolbd == getString(R.string.last_blood_donation)) {
            binding.lastBloodDonationTv.error = ("Date Of last blood Required!")
            return
        }


        if (commonDateUtilities.getYearDifference(dob) < 18) {
            binding.lastBloodDonationTv.error = ("Must be at least 3 years old")
            binding.lastBloodDonationTv.requestFocus()
            Toast.makeText(requireContext(), "Must be at least 3 years old", Toast.LENGTH_SHORT)
                .show()
            return
        }
        val spinner = requireActivity().findViewById(R.id.blood_groupSpinner) as Spinner
        val bg_text = spinner.selectedItem.toString()
        with(registerMap) {
            if (TextUtils.isEmpty(dob) || dob == getString(R.string.dob)) {
                binding.dobTv.error = ("Date Of Birth Required!")
                return
            }

            if (commonDateUtilities.getYearDifference(dob) < 18) {
                binding.dobTv.error = ("Must be at least 18 years old")
                binding.dobTv.requestFocus()
                Toast.makeText(requireContext(), "Must be at least 18 years old", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            put("name",binding.Name.text.toString())
            put("phone",binding.phoneNumber.text.toString())
            put("blood_group",bg_text)
            /*put("division_id",binding.lastName.text.toString())*/
            /*put("district_id",binding.lastName.text.toString())*/
            /*put("upazila_id",binding.lastName.text.toString())*/
            put("dob",binding.dobTv.text.toString())
            val segmentedButtonGroup: SegmentedGroup =
                (binding.gender)
            radioButton = requireActivity().findViewById(segmentedButtonGroup.checkedRadioButtonId)
            Log.d("TAG", "verifyData: "+radioButton.text)
            put("gender",radioButton.text)
            put("last_donate",binding.dobTv.text.toString())

        }

        if (CheckInternetConnection.isInternetAvailable(context)) {
            MainScope().launch(Dispatchers.Main) {
                presenter.signUpDataResponse(registerMap)
                Log.d("TAG", "verifyData: $registerMap")
            }
        }else{
            Toast.makeText(requireContext(), "Internet is not available!", Toast.LENGTH_SHORT).show()
            return
        }


    }
    private fun initCalender() {
        date =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel(myCalendar)
            }
    }


    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.dobTv.text = sdf.format(myCalendar.time)
    }

    fun dobStatus() {
        myCalendar = Calendar.getInstance()
        if (TextUtils.isEmpty(binding.dobTv.text) || binding.dobTv.text == getString(R.string.dob)) {
            myCalendar.add(Calendar.YEAR, -18)
        } else {
            val date: String = binding.dobTv.text.toString()
            val year: Int = commonDateUtilities.getYearDifferenceforshow(date)
            myCalendar = Calendar.getInstance()
            myCalendar.add(Calendar.YEAR, -year)
        }

        DatePickerDialog(
            requireContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
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

    override fun onRegisterData(signUpModel: SignUpModel) {
        Log.d("TAG", "onRegisterData: $signUpModel")
    }

    override fun onRegisterStartLoading() {
        dialog.show()
    }

    override fun onRegisterStopLoading() {
        dialog.hide()
    }

    override fun onRegisterShowMessage(errMsg: String?) {
        Toast.makeText(context,errMsg,Toast.LENGTH_SHORT)
    }

    override fun onRegisterTime(secMsg: String?) {
        TODO("Not yet implemented")
    }
}