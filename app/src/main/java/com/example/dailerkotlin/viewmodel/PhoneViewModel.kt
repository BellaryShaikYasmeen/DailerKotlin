package com.example.dailerkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PhoneViewModel : ViewModel() {
    private val _phoneNumber = MutableLiveData("")
    val phoneNumber: LiveData<String> get() = _phoneNumber

    fun appendNumber(digit: String) {
        _phoneNumber.value += digit
    }

    fun deleteLast() {
        _phoneNumber.value = _phoneNumber.value?.dropLast(1) ?: ""
    }

    fun clearAll() {
        _phoneNumber.value = ""
    }
}
