package com.test.italika.base

import androidx.fragment.app.Fragment
import com.test.italika.AndroidApp


abstract class BaseFragment : Fragment() {

    val Fragment.app: AndroidApp
        get() = activity?.application as AndroidApp

    private val loadingFragment: LoadingFragment = LoadingFragment()

    fun showLoading() {
        if (!loadingFragment.isAdded) {
            fragmentManager?.let {
                loadingFragment.show(it, "loading")
                loadingFragment.isCancelable = false
            }
        }else{
            return
        }
    }

    fun hideLoading() {
        if (loadingFragment.isAdded) {
            loadingFragment.dismiss()
        }
    }

    fun seTitleLoading(fileName:String){
        if (!loadingFragment.isAdded) {
            loadingFragment.setTitle(fileName)
        }
    }

}