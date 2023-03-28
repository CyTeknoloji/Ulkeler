package com.caneryildirim.ulkeler.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caneryildirim.ulkeler.R
import com.caneryildirim.ulkeler.databinding.FragmentDetailBinding
import com.caneryildirim.ulkeler.databinding.FragmentFeedBinding
import com.caneryildirim.ulkeler.util.downloadUrl
import com.caneryildirim.ulkeler.util.isNull
import com.caneryildirim.ulkeler.viewModel.DetailViewModel
import com.caneryildirim.ulkeler.viewModel.FeedViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentDetailBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(DetailViewModel::class.java)
        arguments?.let {
            val countryUUID=DetailFragmentArgs.fromBundle(it).countryUUID
            viewModel.getDataFromRoom(countryUUID,requireContext())
        }


        observeLiveData()


    }

    private fun observeLiveData() {
        viewModel.countryLive.observe(viewLifecycleOwner, Observer {
            binding.textDetailCountry.text=it.countryName
            binding.textDetailCapital.text=it.countryCapital
            binding.textDetailCurrency.text=it.countryCurrency
            binding.textDetailLanguage.text=it.countryLanguage
            binding.textDetailRegion.text=it.countryRegion
            binding.imageDetailFlag.downloadUrl(it.imageUrl!!)
        })
    }


}