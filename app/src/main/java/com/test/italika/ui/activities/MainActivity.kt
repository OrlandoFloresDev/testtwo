package com.test.italika.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.test.italika.R
import com.test.italika.base.BaseActivity
import com.test.italika.base.BaseViewModel
import com.test.italika.databinding.ActivityMainBinding
import com.test.italika.ui.fragments.GalleryFragment
import com.test.italika.ui.fragments.LocationFragment
import com.test.italika.ui.fragments.ViewPagerFragment
import com.test.italika.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    //region Activity overrides methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setCurrentFragment(ViewPagerFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.movies -> setCurrentFragment(ViewPagerFragment())
                R.id.location -> setCurrentFragment(LocationFragment())
                R.id.galeria -> setCurrentFragment(GalleryFragment())
            }
            true
        }
    }
    // endregion

    //region Base class and interface override methods
    override fun setListeners() {

    }

    override fun setViewModel(): BaseViewModel {
        return viewModel
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    //endregion
}
