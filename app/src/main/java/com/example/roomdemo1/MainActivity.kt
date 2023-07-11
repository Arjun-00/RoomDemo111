
package com.example.roomdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo1.databinding.ActivityMainBinding
import com.example.roomdemo1.db.SubscribeDataBase
import com.example.roomdemo1.db.Subscriber
import com.example.roomdemo1.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    //https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-a
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : MyRecyclerViewAdapter
    private lateinit var subscriberViewModel : SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = SubscribeDataBase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        subscriberViewModel.message.observe(this,Observer{
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun displaySubscriberList(){
     subscriberViewModel.suscribers.observe(this, Observer {
         Log.i("MYTAF",it.toString())
         binding.subscriberRecyclerview.adapter = MyRecyclerViewAdapter(
             it,{selectedItem : Subscriber -> listItemClicked(selectedItem)})
         //adapter.setList(it)
     })


    }

    private fun initRecyclerView(){
        // adapter= MyRecyclerViewAdapter({selectedItem : Subscriber -> listItemClicked(selectedItem)})
        // binding.subscriberRecyclerView.adapter = adapter
        binding.subscriberRecyclerview.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()


    }

    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(this,"subscriber name is ${subscriber.name}",Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }

}