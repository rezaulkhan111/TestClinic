package com.example.testclinic

import androidx.lifecycle.ViewModel
import com.example.testclinic.data.model.User

class SharedSVM : ViewModel() {
    var mUser: User? = User()
    var mUserId: Int? = 0
}