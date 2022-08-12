package com.test.italika.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.test.italika.R
import com.test.italika.base.BaseFragment
import com.test.italika.databinding.FragmentGalleryBinding
import com.test.italika.extensions.sendNotificationConpact
import com.test.italika.viewmodels.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val REQUEST_CODE = 200
    var count = 1
    var totalCount = 0
    private lateinit var viewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = binding.btnSubir
        button.setOnClickListener {
            fileUpload()
        }

        viewModel.response.observe(viewLifecycleOwner) {
            if (it) {
                if (count == 1) {
                    hideLoading()
                    activity!!.sendNotificationConpact(
                        getString(R.string.title_upload),
                        getString(R.string.title_Description, totalCount)
                    )
                } else count--
            }
        }
    }

    private fun fileUpload() {

        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures"), REQUEST_CODE
            )
        } else { // For latest versions API LEVEL 19+
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        showLoading()

        if (requestCode == REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            if (data?.clipData != null) {
                totalCount = data.clipData!!.itemCount
                count = totalCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    viewModel.uploadImages(imageUri)
                }
            } else if (data?.data != null) {
                totalCount = 1
                var imageUri: Uri = data.data!!
                viewModel.uploadImages(imageUri)
            }
        }
    }
}
