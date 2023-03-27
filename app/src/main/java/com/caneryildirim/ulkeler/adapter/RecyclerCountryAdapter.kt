package com.caneryildirim.ulkeler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.caneryildirim.ulkeler.databinding.RecyclerRowBinding
import com.caneryildirim.ulkeler.model.Country
import com.caneryildirim.ulkeler.util.downloadUrl
import com.caneryildirim.ulkeler.view.FeedFragmentDirections

class RecyclerCountryAdapter(val countryList:ArrayList<Country>):RecyclerView.Adapter<RecyclerCountryAdapter.CountryHolder>() {

    class CountryHolder(val binding:RecyclerRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.binding.textCountryName.text=countryList[position].countryName
        holder.binding.textRegion.text=countryList[position].countryRegion
        holder.binding.imageFlag.downloadUrl(countryList[position].imageUrl!!)
        holder.itemView.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToDetailFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

}