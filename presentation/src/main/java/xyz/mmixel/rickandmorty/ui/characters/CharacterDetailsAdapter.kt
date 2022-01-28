package xyz.mmixel.rickandmorty.ui.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.mmixel.domain.entities.network.episodes.EpisodeDetails
import xyz.mmixel.rickandmorty.databinding.EpisodeItemBinding

class CharacterDetailsAdapter(private val onClick: (EpisodeDetails) -> Unit) :
    RecyclerView.Adapter<CharacterDetailsAdapter.ViewHolder>() {
    private var episodesList: List<EpisodeDetails> = emptyList()

    class ViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodesValue: EpisodeDetails) {
            with(binding) {
                episodeNameTextView.text = episodesValue.name
                episodeNumberTextView.text = episodesValue.episode
                airDateTextView.text = episodesValue.airDate
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
        val item = episodesList[position]
        holder.itemView.setOnClickListener { onClick(item) }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return episodesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(episodesList: List<EpisodeDetails>) {
        this.episodesList = episodesList
        notifyDataSetChanged()
    }
}