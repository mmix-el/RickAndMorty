package xyz.mmixel.rickandmorty.ui.episodes

import android.os.Bundle
import android.view.*
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
import xyz.mmixel.domain.queries.EpisodeQuery
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.FragmentEpisodesBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewPager.ViewPagerFragmentDirections
import xyz.mmixel.rickandmorty.utils.RecyclerViewLoadStateAdapter
import xyz.mmixel.rickandmorty.ui.viewmodel.EpisodesViewModel
import javax.inject.Inject

class EpisodesFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var adapter: EpisodesAdapter
    private var searchJob: Job? = null
    private var episodeQuery: EpisodeQuery? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<EpisodesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusEpisodesFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        adapter = EpisodesAdapter { episodeDetails ->
            val action =
                ViewPagerFragmentDirections.actionMainFragmentToEpisodeDetailsFragment(episodeDetails.id)
            findNavController().navigate(action)
            setMenuVisibility(false)
        }

        with(binding) {
            episodesRecyclerView.adapter = adapter.withLoadStateFooter(
                RecyclerViewLoadStateAdapter()
            )

            episodesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_episode")
                ?.observe(
                    viewLifecycleOwner
                ) { json ->
                    episodeQuery =
                        Gson().fromJson(json, object : TypeToken<EpisodeQuery>() {}.type)
                    episodeQuery?.let { startFilterJob(it) }
                }

            swipeRefreshLayout.setOnRefreshListener {
                if (episodeQuery != null) {
                    episodeQuery?.let { startFilterJob(it) }
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
            viewModel.getEpisodes().observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun startFilterJob(query: EpisodeQuery) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredEpisodes(query).observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter.addLoadStateListener { loadState ->
            with(binding) {
                if (loadState.refresh is LoadState.Loading) {
                    nothingLabelTextView.visibility = View.GONE
                    episodesRecyclerView.visibility = View.GONE
                    progressIndicator.isVisible = true
                } else {
                    progressIndicator.isVisible = false
                    episodesRecyclerView.visibility = View.VISIBLE

                    if (adapter.itemCount == 0) {
                        nothingLabelTextView.visibility = View.VISIBLE
                    }
                }
            }

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            episodeQuery = EpisodeQuery("%$query%", null)
            episodeQuery?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            episodeQuery = EpisodeQuery("%$query%", null)
            episodeQuery?.let { startFilterJob(it) }
        }

        return true
    }
}