package xyz.mmixel.rickandmorty.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import xyz.mmixel.rickandmorty.*
import xyz.mmixel.rickandmorty.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        setHasOptionsMenu(true)

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            CHARACTERS_PAGE_INDEX -> R.drawable.ic_characters
            EPISODES_PAGE_INDEX -> R.drawable.ic_episodes
            LOCATIONS_PAGE_INDEX -> R.drawable.ic_locations
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            CHARACTERS_PAGE_INDEX -> getString(R.string.characters_title)
            EPISODES_PAGE_INDEX -> getString(R.string.episodes_title)
            LOCATIONS_PAGE_INDEX -> getString(R.string.locations_title)
            else -> null
        }
    }
}

interface ViewPagerListener {
    fun pageChanged(stringId: Int)
}