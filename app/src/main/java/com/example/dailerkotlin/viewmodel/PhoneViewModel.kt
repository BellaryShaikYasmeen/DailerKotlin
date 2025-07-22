package com.example.dialerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhoneViewModel : ViewModel() {
    val phoneNumber = MutableLiveData("")

    fun appendNumber(num: String) {
        phoneNumber.value += num
    }

    fun deleteLast() {
        phoneNumber.value = phoneNumber.value?.dropLast(1)
    }

    fun clearAll() {
        phoneNumber.value = ""
    }
}