package xyz.mmixel.rickandmorty.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import xyz.mmixel.domain.utils.RegexUtil
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.FragmentCharacterDetailsBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewmodel.details.CharacterDetailsViewModel
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args by navArgs<CharacterDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CharacterDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusCharacterDetailsFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)

        val adapter = CharacterDetailsAdapter { episodeDetails ->
            val action =
                CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToEpisodeDetailsFragment(
                    episodeDetails.id
                )
            findNavController().navigate(action)
        }

        with(binding) {
            collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(requireContext(), R.color.expanded_toolbar_text_color)
            )

            collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(requireContext(), R.color.collapsed_toolbar_text_color)
            )
            characterEpisodesRecyclerView.adapter = adapter

        }


        viewModel.characterDetailsLiveData.observe(viewLifecycleOwner, { characterDetails ->
            with(binding) {
                collapsingToolbarLayout.title = characterDetails.name
                statusTextView.text = "Status: ${characterDetails.status}"
                speciesTextView.text = "Species: ${characterDetails.species}"
                genderTextView.text = "Gender: ${characterDetails.gender}"
                originCardView.setOnClickListener {
                    if (characterDetails.origin.name == "unknown") {
                        Toast.makeText(requireContext(), "Unknown place", Toast.LENGTH_SHORT).show()
                    } else {
                        val action =
                            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToLocationDetailsFragment(
                                RegexUtil.extractIdFromUrl(characterDetails.origin.url)
                            )
                        findNavController().navigate(action)
                    }
                }
                originNameTextView.text = "Origin: ${characterDetails.origin.name}"
                locationCardView.setOnClickListener {
                    if (characterDetails.location.name == "unknown") {
                        Toast.makeText(requireContext(), "Unknown place", Toast.LENGTH_SHORT).show()
                    } else {
                        val action =
                            CharacterDetailsFragmentDirections.actionCharacterDetailsFragmentToLocationDetailsFragment(
                                RegexUtil.extractIdFromUrl(characterDetails.location.url)
                            )
                        findNavController().navigate(action)
                    }
                }
                locationNameTextView.text = "Location: ${characterDetails.location.name}"
                characterImageView.let {
                    Glide
                        .with(requireContext())
                        .load(characterDetails.image)
                        .placeholder(
                            ContextCompat.getDrawable(requireContext(), R.drawable.placeholder)
                        )
                        .into(it)
                }
            }
        })

        viewModel.episodesLiveData.observe(viewLifecycleOwner, { episodes ->
            adapter.setData(episodes)
            binding.episodesLabelTextView.text =
                resources.getString(R.string.episodes) + " (${episodes.size})"
        })

        viewModel.getCharacterById(args.characterId)

        return binding.root
    }
}