package xyz.mmixel.rickandmorty.ui.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.mmixel.domain.entities.network.locations.LocationDetails
import xyz.mmixel.rickandmorty.databinding.LocationItemBinding
import xyz.mmixel.rickandmorty.utils.DiffUtilCallBack

class LocationsAdapter(private val onClick: (LocationDetails) -> Unit) :
    PagingDataAdapter<LocationDetails, LocationsAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationDetails: LocationDetails) {
            with(binding) {
                locationNameTextView.text = locationDetails.name
                typeTextView.text = locationDetails.type
                dimensionTextView.text = locationDetails.dimension
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { locationDetails ->
            holder.itemView.setOnClickListener { onClick(locationDetails) }
            holder.bind(locationDetails)
        }
    }
}