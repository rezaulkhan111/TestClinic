package com.example.testclinic.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

open class GenericResponse {
    @SerializedName("field")
    var field: String? = null

    @SerializedName("message")
    var message: String? = null
}