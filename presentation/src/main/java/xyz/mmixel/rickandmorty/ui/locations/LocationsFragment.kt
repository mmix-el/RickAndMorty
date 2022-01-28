package xyz.mmixel.rickandmorty.ui.locations

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import xyz.mmixel.domain.queries.LocationQuery
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.FragmentLocationsBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewPager.ViewPagerFragmentDirections
import xyz.mmixel.rickandmorty.ui.viewmodel.LocationsViewModel
import xyz.mmixel.rickandmorty.utils.RecyclerViewLoadStateAdapter
import javax.inject.Inject

class LocationsFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentLocationsBinding
    private lateinit var adapter: LocationsAdapter
    private var searchJob: Job? = null
    private var locationQuery: LocationQuery? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LocationsViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.plusLocationsFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)

        adapter = LocationsAdapter { locationDetails ->
            val action =
                ViewPagerFragmentDirections.actionMainFragmentToLocationDetailsFragment(locationDetails.id)
            findNavController().navigate(action)
            setMenuVisibility(false)
        }

        with(binding) {
            locationsRecyclerView.adapter = adapter.withLoadStateFooter(
                RecyclerViewLoadStateAdapter()
            )

            locationsRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_location")
                ?.observe(
                    viewLifecycleOwner
                ) { json ->
                    locationQuery =
                        Gson().fromJson(json, object : TypeToken<LocationQuery>() {}.type)
                    locationQuery?.let { startFilterJob(it) }
                }

            swipeRefreshLayout.setOnRefreshListener {
                if (locationQuery != null) {
                    locationQuery?.let { startFilterJob(it) }
                } else {
                    startSearchJob()
                }

                adapter.refresh()
                swipeRefreshLayout.isRefreshing = false
            }
        }


        startSearchJob()
        setUpAdapter()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    private fun startSearchJob() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getLocations().observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun startFilterJob(query: LocationQuery) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredLocations(query).observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter.addLoadStateListener { loadState ->
            with(binding) {
                if (loadState.refresh is LoadState.Loading) {
                    nothingLabelTextView.visibility = View.GONE
                    locationsRecyclerView.visibility = View.GONE
                    progressIndicator.isVisible = true
                } else {
                    progressIndicator.isVisible = false
                    locationsRecyclerView.visibility = View.VISIBLE

                    if (adapter.itemCount == 0) {
                        nothingLabelTextView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            locationQuery = LocationQuery("%$query%", null, null)
            locationQuery?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            locationQuery = LocationQuery("%$query%", null, null)
            locationQuery?.let { startFilterJob(it) }
        }

        return true
    }
}