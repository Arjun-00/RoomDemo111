package com.example.roomdemo1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdemo1.db.SubscriberRepository

class SubscriberViewModelFactory(private val repository : SubscriberRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SubscriberViewModel::class.java)){
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}