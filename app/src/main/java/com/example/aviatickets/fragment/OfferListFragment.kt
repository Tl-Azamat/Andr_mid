package com.example.aviatickets.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.entity.FlightResponse
import com.example.aviatickets.model.entity.Offer
import com.example.aviatickets.model.network.ApiClient
import com.example.aviatickets.model.network.ApiService
import com.example.aviatickets.model.service.FakeService
import retrofit2.Call
import retrofit2.Response


class OfferListFragment : Fragment() {

    private var currentOffers: List<Offer> = emptyList()
    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        ApiClient.apiService.getFlights().enqueue(object : retrofit2.Callback<List<Offer>> {
            override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                if (response.isSuccessful) {
                    currentOffers = response.body() ?: emptyList()
                    if (currentOffers != null) {
                        adapter.submitList(currentOffers)
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
            }
        })

    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        val sortedByPrice = currentOffers.sortedBy { it.price }
                        adapter.submitList(sortedByPrice.toList())
                    }
                    R.id.sort_by_duration -> {
                        val sortedByDuration = currentOffers.sortedBy { it.flight.duration }
                        adapter.submitList(sortedByDuration.toList())
                    }
                }
            }
        }
    }
}