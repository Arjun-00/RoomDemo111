package com.example.roomdemo1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscribeDataBase : RoomDatabase() {

    abstract val subscriberDAO : SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE : SubscribeDataBase? = null
        fun getInstance(context : Context):SubscribeDataBase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscribeDataBase::class.java,
                        "subscribe_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}