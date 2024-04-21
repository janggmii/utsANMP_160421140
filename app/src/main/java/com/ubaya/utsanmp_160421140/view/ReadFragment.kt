package com.ubaya.utsanmp_160421140.view

import android.database.Observable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.squareup.picasso.Picasso
import com.ubaya.utsanmp_160421140.R
import com.ubaya.utsanmp_160421140.databinding.FragmentReadBinding
import com.ubaya.utsanmp_160421140.viewmodel.ReadViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReadFragment : Fragment() {
    private lateinit var binding:FragmentReadBinding
    private lateinit var readViewModel: ReadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null) {
            val hobbyName = ReadFragmentArgs.fromBundle(requireArguments()).hobbyId
            readViewModel = ViewModelProvider(this).get(ReadViewModel::class.java)
            readViewModel.fetch(hobbyName)
        }
        observeRead()
    }

    fun observeRead(){
        readViewModel.readLD.observe(viewLifecycleOwner, Observer {


            binding.txtTitleView.setText(readViewModel.readLD.value?.title)
            binding.txtCreatorView.setText(readViewModel.readLD.value?.creator)
            binding.txtSubTitleView.setText(readViewModel.readLD.value?.subTitle)
            binding.txtDescView.setText(readViewModel.readLD.value?.description)

            val photo = this.context?.let { it -> Picasso.Builder(it) }
            photo?.listener{ photo, uri, exception ->
                exception.printStackTrace()
            }
            photo?.build()?.load(readViewModel.readLD.value?.pictureUrl)?.into(binding.imageRead)
        })
    }
}