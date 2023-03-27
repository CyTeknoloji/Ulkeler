package com.caneryildirim.ulkeler.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caneryildirim.ulkeler.adapter.RecyclerCountryAdapter
import com.caneryildirim.ulkeler.databinding.FragmentFeedBinding
import com.caneryildirim.ulkeler.viewModel.FeedViewModel


class FeedFragment : Fragment() {
    private var _binding:FragmentFeedBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel:FeedViewModel
    private val recyclerCountryAdapter=RecyclerCountryAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentFeedBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.getContext(requireContext())
        viewModel.refreshData(requireContext())
        observeLiveData()

        binding.recyclerFeed.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerFeed.adapter=recyclerCountryAdapter

        binding.swipeFeed.setOnRefreshListener {
            viewModel.refreshDataForSwipe(requireContext())
            binding.swipeFeed.isRefreshing=false
        }


    }

    private fun observeLiveData() {
        viewModel.countryListLive.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.recyclerFeed.visibility=View.VISIBLE
                recyclerCountryAdapter.updateCountryList(it)
            }
        })

        viewModel.uploadDataLive.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.progressBarFeed.visibility=View.VISIBLE
                binding.recyclerFeed.visibility=View.GONE
                binding.textError.visibility=View.GONE
            }else{
                binding.progressBarFeed.visibility=View.GONE
            }
        })

        viewModel.errorDataLive.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.textError.visibility=View.VISIBLE
                binding.recyclerFeed.visibility=View.GONE
            }else{
                binding.textError.visibility=View.GONE
                binding.recyclerFeed.visibility=View.VISIBLE
            }
        })
    }


}