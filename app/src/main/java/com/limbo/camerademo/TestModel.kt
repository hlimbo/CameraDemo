package com.limbo.camerademo

import com.google.gson.annotations.SerializedName

data class TestModel(@SerializedName("testField") val data: String)