package com.ubaya.utsanmp_160421140.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.utsanmp_160421140.databinding.FragmentHobbyListBinding
import com.ubaya.utsanmp_160421140.model.Hobby
import com.ubaya.utsanmp_160421140.util.loadImage
import java.lang.Exception

class HobbyListAdapter(val hobbyList:ArrayList<Hobby>):RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(val binding:FragmentHobbyListBinding):RecyclerView.ViewHolder(binding.root)

    fun updateHobbyList(newHobbyList:ArrayList<Hobby>){
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = FragmentHobbyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        val hobbyList = hobbyList[position]
        hobbyList.pictureUrl?.let { holder.binding.imageHobby.loadImage(it, holder.binding.progressBarHobby) }
        hobbyList.title.let { holder.binding.txtTitle.text }
        hobbyList.creator.let { holder.binding.txtCreator.text }
        hobbyList.description.let { holder.binding.txtDesc.text }

        holder.binding.btnRead.setOnClickListener{
            val actionRead = hobbyList.id?.toString()
                ?.let { it1 -> HomeFragmentDirections.actionRead(it1) }
            actionRead?.let { it1 -> Navigation.findNavController(it).navigate(it1) }
        }

        val photo = Picasso.Builder(holder.itemView.context)
        photo.listener{photo, uri, exception ->
            exception.printStackTrace()
        }
        photo.build().load(hobbyList.pictureUrl).into(holder.binding.imageHobby)
        photo.build().load(hobbyList.pictureUrl)
            .into(holder.binding.imageHobby, object: Callback {
                override fun onSuccess() {
                    holder.binding.progressBarHobby.visibility = View.INVISIBLE
                    holder.binding.imageHobby.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.d("Picasso Error", e.toString())
                }
            })
    }

}