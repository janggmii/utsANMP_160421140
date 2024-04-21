package com.ubaya.utsanmp_160421140.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.utsanmp_160421140.R
import com.ubaya.utsanmp_160421140.databinding.FragmentHomeBinding
import com.ubaya.utsanmp_160421140.viewmodel.HobbyListViewModel

class HomeFragment : Fragment() {
    private lateinit var listViewModel:HobbyListViewModel
    private val hobbyListAdapter = HobbyListAdapter(arrayListOf())
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel = ViewModelProvider(this)[HobbyListViewModel::class.java]
        listViewModel.fetch()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = hobbyListAdapter

        binding.SwipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            listViewModel.fetch()
            binding.SwipeRefreshLayout.isRefreshing = false
        }
        observeListViewModel()
    }

    private fun observeListViewModel(){
        listViewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            hobbyListAdapter.updateHobbyList(it)
        })

        listViewModel.loadingErrorLD.observe(viewLifecycleOwner) {
            if(it){
                binding.txtError.visibility = View.VISIBLE
            }else{
                binding.txtError.visibility = View.GONE
            }
        }

        listViewModel.loadingLD.observe(viewLifecycleOwner) {
            if(it){
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }
}