package com.example.testclinic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testclinic.data.ApiRepositoryImpl
import com.example.testclinic.data.model.GenericResponse
import com.example.testclinic.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CommonVM @Inject constructor(private val apiRepo: ApiRepositoryImpl) : ViewModel() {

    var successUsersLD = MutableLiveData<List<User>>()
    var successUserLD = MutableLiveData<User>()
    var fieldCommonLD = MutableLiveData<GenericResponse>()

    fun fetchAllUsers() {
        apiRepo.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if (response.isSuccessful) {
                    successUsersLD.value = response.body()
                } else {
                    fieldCommonLD.value = getErrorBody(response.errorBody())
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                fieldCommonLD.value = GenericResponse().apply { message = t.message }
            }
        })
    }

    fun saveUser(mUser: User) {
        apiRepo.saveUsers(mUser).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    successUserLD.value = response.body()
                } else {
                    fieldCommonLD.value = getErrorBody(response.errorBody())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                fieldCommonLD.value = GenericResponse().apply { message = t.message }
            }
        })
    }

    fun updateUser(mUser: User) {
        apiRepo.saveUsers(mUser).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    successUserLD.value = response.body()
                } else {
                    fieldCommonLD.value = getErrorBody(response.errorBody())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                fieldCommonLD.value = GenericResponse().apply { message = t.message }
            }
        })
    }

    fun getErrorBody(errorBody: ResponseBody?): GenericResponse {
        return Gson().fromJson(
            JSONObject(errorBody!!.string()).toString(), GenericResponse::class.java
        ) as GenericResponse
    }
}