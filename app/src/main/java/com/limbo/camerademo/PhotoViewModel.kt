package com.limbo.camerademo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import java.io.File

class PhotoViewModel: ViewModel() {
    private val repo: LocalServerRepo by lazy {
        LocalServerRepo()
    }

    fun uploadPhoto(file: File): LiveData<PhotoModel> {
        return repo.uploadPhoto(file)
    }

    fun testRoute(): LiveData<TestModel> {
        return repo.testRoute()
    }
}