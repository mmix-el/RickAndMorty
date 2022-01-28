package xyz.mmixel.rickandmorty.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.EpisodeCharacterItemBinding

class LocationDetailsAdapter(private val onClick: (CharacterDetails) -> Unit) :
    RecyclerView.Adapter<LocationDetailsAdapter.ViewHolder>() {
    private var charactersList: List<CharacterDetails> = emptyList()

    class ViewHolder(private val binding: EpisodeCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(charactersValue: CharacterDetails) {
            with(binding) {
                nameTextView.text = charactersValue.name
                statusTextView.text = charactersValue.status
                speciesTextView.text = charactersValue.species
                genderTextView.text = charactersValue.gender
                Glide
                    .with(root.context)
                    .load(charactersValue.image)
                    .placeholder(
                        ContextCompat.getDrawable(root.context, R.drawable.placeholder)
                    )
                    .into(characterImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EpisodeCharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = charactersList[position]

        holder.itemView.setOnClickListener { onClick(item) }

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(charactersList: List<CharacterDetails>) {
        this.charactersList = charactersList
        notifyDataSetChanged()
    }
}