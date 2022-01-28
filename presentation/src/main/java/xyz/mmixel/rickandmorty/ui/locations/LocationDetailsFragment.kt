package xyz.mmixel.rickandmorty.ui.locations

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
import xyz.mmixel.rickandmorty.databinding.FragmentLocationDetailsBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewmodel.details.LocationDetailsViewModel
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {
    private lateinit var binding: FragmentLocationDetailsBinding
    private val args by navArgs<LocationDetailsFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LocationDetailsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusLocationDetailsFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater)

        val adapter = LocationDetailsAdapter { characterDetails ->
            val action = LocationDetailsFragmentDirections.actionLocationDetailsFragmentToCharacterDetailsFragment(characterDetails.id)
            findNavController().navigate(action)
        }
        with(binding) {
            locationResidentsRecyclerView.adapter = adapter

            viewModel.locationDetailsLiveData.observe(viewLifecycleOwner, { locationDetails ->
                locationNameTextView.text = "Name: ${locationDetails.name}"
                typeTextView.text = "Type: ${locationDetails.type}"
                dimensionTextView.text = "Dimension: ${locationDetails.dimension}"
            })

            viewModel.residentsLiveData.observe(viewLifecycleOwner, { residents ->
                adapter.setData(residents)

                residentsLabelTextView.text =
                    resources.getString(R.string.residents) + " (${residents.size})"
            })

        }

        viewModel.getLocationById(args.locationId)

        return binding.root
    }
}