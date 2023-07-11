package com.example.roomdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo1.db.Subscriber
import com.example.roomdemo1.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriberViewModel(private val repository : SubscriberRepository) : ViewModel() {
    val suscribers = repository.subscriberList

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllorDeleteButtonText = MutableLiveData<String>()
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber
    private val statusMessage = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>> get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllorDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
            statusMessage.value = Event("Updated Successfully")
        }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputName.value = ""
            inputEmail.value = ""
        }

    }

    fun clearAllorDelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
            statusMessage.value = Event("Deleted Successfully")
        }else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertSubscriberData(subscriber)
         //   statusMessage.value = Event("Inserted Successfully")
        }
    fun update(subscriber: Subscriber) = viewModelScope.launch (Dispatchers.IO){
        repository.updateSubscriberData(subscriber)
        withContext(Dispatchers.IO){
            inputName.postValue("")
            inputEmail.postValue("")
            isUpdateOrDelete = false
            saveOrUpdateButtonText.postValue("Save")
            clearAllorDeleteButtonText.postValue("Update")
            //statusMessage.value = Event("Updated Successfully")
        }
    }
    fun delete(subscriber: Subscriber) = viewModelScope.launch (Dispatchers.IO){
        repository.suscriberDelete(subscriber)
    }
    fun deleteAll() = viewModelScope.launch (Dispatchers.IO){ repository.deleteAll()
    withContext(Dispatchers.IO){
        inputName.postValue("")
        inputEmail.postValue("")
        isUpdateOrDelete = false
        saveOrUpdateButtonText.postValue("Save")
        clearAllorDeleteButtonText.postValue("Update")
       // statusMessage.value = Event("Deleted Successfully")
    }
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.postValue(subscriber.name)
        inputEmail.postValue( subscriber.email)
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.postValue("Update")
        clearAllorDeleteButtonText.postValue("Delete")
    }


}