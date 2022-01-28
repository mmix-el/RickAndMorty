package xyz.mmixel.rickandmorty.ui.characters

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
import xyz.mmixel.domain.queries.CharacterQuery
import xyz.mmixel.rickandmorty.R
import xyz.mmixel.rickandmorty.databinding.FragmentCharactersBinding
import xyz.mmixel.rickandmorty.di.Injector
import xyz.mmixel.rickandmorty.ui.viewPager.ViewPagerFragmentDirections
import xyz.mmixel.rickandmorty.utils.RecyclerViewLoadStateAdapter
import xyz.mmixel.rickandmorty.ui.viewmodel.CharactersViewModel
import javax.inject.Inject

class CharactersFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var adapter: CharactersAdapter
    private var searchJob: Job? = null
    private var characterQuery: CharacterQuery? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CharactersViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector.plusCharactersFragmentComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)

        adapter = CharactersAdapter { characterValue ->
            val action =
                ViewPagerFragmentDirections.actionMainFragmentToCharacterDetailsFragment(
                    characterValue.id
                )
            findNavController().navigate(action)
            setMenuVisibility(false)
        }

        with(binding) {
            charactersRecyclerView.adapter = adapter.withLoadStateFooter(
                RecyclerViewLoadStateAdapter()
            )

            charactersRecyclerView.layoutManager =
                StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filter_character")
                ?.observe(
                    viewLifecycleOwner
                ) { json ->
                    characterQuery =
                        Gson().fromJson(json, object : TypeToken<CharacterQuery>() {}.type)
                    characterQuery?.let { startFilterJob(it) }
                }

            swipeRefreshLayout.setOnRefreshListener {
                if (characterQuery != null) {
                    characterQuery?.let { startFilterJob(it) }
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
            viewModel.getCharacters().observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
                binding.charactersRecyclerView.layoutAnimation
            })
        }
    }

    private fun startFilterJob(query: CharacterQuery) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getFilteredCharacters(query).observe(viewLifecycleOwner, {
                adapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {
        adapter.addLoadStateListener { loadState ->
            with(binding) {
                if (loadState.refresh is LoadState.Loading) {
                    nothingLabelTextView.visibility = View.GONE
                    charactersRecyclerView.visibility = View.GONE
                    progressIndicator.isVisible = true
                } else {
                    progressIndicator.isVisible = false
                    charactersRecyclerView.visibility = View.VISIBLE

                    if (adapter.itemCount == 0) {
                        nothingLabelTextView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            characterQuery =
                CharacterQuery(
                    "%$query%",
                    null,
                    null,
                    null,
                    null
                )
            characterQuery?.let { startFilterJob(it) }
        }

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            characterQuery =
                CharacterQuery(
                    "%$query%",
                    null,
                    null,
                    null,
                    null
                )
            characterQuery?.let { startFilterJob(it) }
        }

        return true
    }
}