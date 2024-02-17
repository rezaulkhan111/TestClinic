package com.example.testclinic.data.model

import com.google.gson.annotations.SerializedName

class User : GenericResponse() {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("status")
    var status: String? = null
}