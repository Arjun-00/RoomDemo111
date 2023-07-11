package com.example.roomdemo1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo1.databinding.ListViewBinding
import com.example.roomdemo1.db.Subscriber

class MyRecyclerViewAdapter(private val subscribers : List<Subscriber>,
private val clickListener : (Subscriber) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val subscriberList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInfalter : LayoutInflater = LayoutInflater.from(parent.context)
        val  binding : ListViewBinding = DataBindingUtil.inflate(layoutInfalter,R.layout.list_view,parent,false)
        return MyViewHolder(binding,clickListener)
    }

    override fun getItemCount(): Int {
       return subscribers.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(subscribers[position],clickListener)
    }

    ///New Adapter changes
    fun setList(subscribersList : List<Subscriber>){
        // subscribersList.clear()
        // subscribersList.addAll(subscriber)
    }


}

class MyViewHolder(val binding : ListViewBinding,clickListener: (Subscriber) -> Unit

) : RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}