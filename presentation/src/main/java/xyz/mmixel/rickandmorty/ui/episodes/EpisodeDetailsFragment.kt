package xyz.mmixel.rickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewmodel.details.EpisodeDetailsViewModel
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEpisodeDetailsBinding
    private val args by navArgs<EpisodeDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EpisodeDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusEpisodeDetailsFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeDetailsBinding.inflate(layoutInflater)

        val adapter = EpisodeDetailsAdapter { characterDetails ->
            val action =
                EpisodeDetailsFragmentDirections.actionEpisodeDetailsFragmentToCharacterDetailsFragment(
                    characterDetails.id
                )
            findNavController().navigate(action)
        }
        with(binding) {
            episodeCharactersRecyclerView.adapter = adapter

            viewModel.episodeDetailsLiveData.observe(viewLifecycleOwner, { episodeDetails ->
                nameTextView.text = "Name: ${episodeDetails.name}"
                airDateTextView.text = "Air Date: ${episodeDetails.airDate}"
                episodeNumberTextView.text = "Episode: ${episodeDetails.episode}"
            })

            viewModel.charactersLiveData.observe(viewLifecycleOwner, { characters ->
                adapter.setData(characters)

                charactersLabelTextView.text =
                    resources.getString(R.string.characters) + " (${characters.size})"
            })
        }
        viewModel.getEpisodeById(args.episodeId)
        return binding.root
    }
}