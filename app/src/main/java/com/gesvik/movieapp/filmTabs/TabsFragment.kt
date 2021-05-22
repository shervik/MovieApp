package com.gesvik.movieapp.filmTabs

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.gesvik.movieapp.R
import com.gesvik.movieapp.databinding.TabsFragmentBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import timber.log.Timber

class TabsFragment: Fragment() {
    private lateinit var binding: TabsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView")

        binding = TabsFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated")

        val tabLayout: TabLayout = binding.tabs
        val viewPager = binding.viewPagerTabs
        val pagerAdapter = FilmsPagerAdapter(childFragmentManager, lifecycle)

        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = resources.getStringArray(R.array.tabs)[position]
            }
        ).attach()

        viewPager.isUserInputEnabled = false

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}