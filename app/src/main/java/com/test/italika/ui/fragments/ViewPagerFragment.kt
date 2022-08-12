package com.test.italika.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.italika.base.BaseFragment
import com.test.italika.databinding.FragmentViewPagerBinding
import com.test.italika.ui.adapters.SectionPagerAdapter
import com.test.italika.viewmodels.GalleryViewModel
import com.test.italika.viewmodels.MoviesViewModel

class ViewPagerFragment : BaseFragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(inflater)
        // Inflate the layout for this fragment
        initUI()

        return binding.root
    }

    private fun initUI() {
        val sectionPagerAdapter =
            SectionPagerAdapter(requireActivity(), requireActivity().supportFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ViewPagerFragment.
         */
        @JvmStatic
        fun newInstance() =
            ViewPagerFragment()
    }
}