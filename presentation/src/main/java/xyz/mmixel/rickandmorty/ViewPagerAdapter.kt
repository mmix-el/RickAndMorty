package xyz.mmixel.rickandmorty

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.mmixel.rickandmorty.ui.characters.CharactersFragment
import xyz.mmixel.rickandmorty.ui.episodes.EpisodesFragment
import xyz.mmixel.rickandmorty.ui.locations.LocationsFragment


const val CHARACTERS_PAGE_INDEX = 0
const val EPISODES_PAGE_INDEX = 1
const val LOCATIONS_PAGE_INDEX = 2

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        CHARACTERS_PAGE_INDEX to { CharactersFragment() },
        EPISODES_PAGE_INDEX to { EpisodesFragment() },
        LOCATIONS_PAGE_INDEX to { LocationsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}