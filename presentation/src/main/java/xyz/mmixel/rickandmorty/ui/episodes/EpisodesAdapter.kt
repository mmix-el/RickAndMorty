package xyz.mmixel.rickandmorty.ui.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.rickandmorty.databinding.EpisodeItemBinding
import xyz.mmixel.rickandmorty.utils.DiffUtilCallBack

class EpisodesAdapter(private val onClick: (EpisodeDetails) -> Unit) :
    PagingDataAdapter<EpisodeDetails, EpisodesAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeDetails: EpisodeDetails) {
            with(binding) {
                episodeNameTextView.text = episodeDetails.name
                episodeNumberTextView.text = episodeDetails.episode
                airDateTextView.text = episodeDetails.airDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { episodeDetails ->
            holder.itemView.setOnClickListener { onClick(episodeDetails) }
            holder.bind(episodeDetails)
        }
    }
}