package com.blood_bag.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.blood_bag.R
import com.blood_bag.databinding.FragmentLandingBinding
import com.example.sample.localDb.AppSessionManager

class LandingFragment : Fragment() {
    lateinit var binding: FragmentLandingBinding
    lateinit  var appSessionManager: AppSessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentLandingBinding>(
            inflater,
            R.layout.fragment_landing, container, false
        )
        appSessionManager = AppSessionManager(requireContext())
        binding.btnContinue.setOnClickListener {
            showFragment(SignInFragment.newInstance())
        }
        return binding.root

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


    companion object {

        @JvmStatic
        fun newInstance() =
            LandingFragment().apply {
            }
    }
}