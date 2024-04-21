package com.ubaya.utsanmp_160421140.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.utsanmp_160421140.databinding.FragmentHobbyListBinding
import com.ubaya.utsanmp_160421140.model.Hobby
import com.ubaya.utsanmp_160421140.util.loadImage

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
            val actionRead = HomeFragmentDirections.actionRead()
            Navigation.findNavController(it).navigate(actionRead)
        }
    }

}