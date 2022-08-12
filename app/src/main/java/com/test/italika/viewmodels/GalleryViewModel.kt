package com.test.italika.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.test.italika.base.BaseViewModel
import com.test.italika.models.User
import com.test.italika.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val firebaseStorage: FirebaseStorage) :
    BaseViewModel() {

    val response = MutableLiveData<Boolean>()

    fun uploadImages(uri: Uri) {
        viewModelScope.launch {
            val folder: StorageReference = firebaseStorage.reference
                .child("Foto")

            val fileName: StorageReference = folder.child("" + uri.lastPathSegment)
            fileName.putFile(uri)
                .addOnSuccessListener {
                    response.postValue(true)
                }.addOnCanceledListener {
                    response.postValue(false)
                }
        }
    }
}