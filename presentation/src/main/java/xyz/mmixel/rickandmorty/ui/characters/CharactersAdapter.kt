package xyz.mmixel.rickandmorty.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.mmixel.domain.entities.network.characters.CharacterDetails
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.CharacterItemBinding
import xyz.mmixel.rickandmorty.utils.DiffUtilCallBack

class CharactersAdapter(private val onClick: (CharacterDetails) -> Unit) :
    PagingDataAdapter<CharacterDetails, CharactersAdapter.ViewHolder>(DiffUtilCallBack()) {

    class ViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(characterDetails: CharacterDetails) {
            with(binding) {
                nameTextView.text = characterDetails.name
                statusTextView.text = characterDetails.status
                speciesTextView.text = characterDetails.species
                genderTextView.text = characterDetails.gender
                Glide
                    .with(root.context)
                    .load(characterDetails.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(
                        ContextCompat.getDrawable(root.context, R.drawable.placeholder)
                    )
                    .into(characterImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        item?.let { characterDetails ->
            holder.itemView.setOnClickListener { onClick(characterDetails) }
            holder.bind(characterDetails)
        }
    }


}