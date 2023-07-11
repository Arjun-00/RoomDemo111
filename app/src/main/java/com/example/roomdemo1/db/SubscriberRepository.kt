package com.example.roomdemo1.db

class SubscriberRepository(private val dao : SubscriberDAO) {
    suspend fun insertSubscriberData(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }
    suspend fun updateSubscriberData(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }
    suspend fun suscriberDelete(subcriber:Subscriber){
        dao.deleteSubscriber(subcriber
        )
    }
    suspend fun deleteAll(){
        dao.deleteAll()
    }
    val subscriberList = dao.getAllSubscribers()
}