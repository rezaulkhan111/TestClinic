package com.example.testclinic.data

import com.example.testclinic.data.model.User
import com.example.testclinic.data.service.ApiService
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) {
    fun getAllUsers() = apiService.getAllUsers()

    fun createUser(mUser: User) =
        apiService.createUser(
            mUser.name.toString(),
            mUser.email.toString(),
            mUser.gender.toString(),
            mUser.status.toString()
        )

    fun updateUsers(mUser: User) = apiService.updateUsers(
        mUser.id,
        mUser.name.toString(),
        mUser.email.toString(),
        mUser.gender.toString(),
        mUser.status.toString()
    )
}