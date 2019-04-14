package com.limbo.camerademo

import android.net.Uri

interface CallbackGlue {
    fun onRegisterObserver(fileUri: Uri)
}