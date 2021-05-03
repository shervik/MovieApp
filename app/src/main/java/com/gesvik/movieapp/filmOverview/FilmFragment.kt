package com.gesvik.movieapp.filmOverview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.gesvik.movieapp.R
import com.gesvik.movieapp.databinding.FilmFragmentBinding
import com.gesvik.movieapp.network.entities.GenresResponse
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import timber.log.Timber
import kotlin.math.abs

class FilmFragment(val category: String) : Fragment() {
    private lateinit var binding: FilmFragmentBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView")

        binding = FilmFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated")


        tabLayout = binding.tabs
        val categoryName = category

        val viewModelFactory = ViewModelFactory(categoryName)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FilmsViewModel::class.java)
        binding.viewModel = viewModel

        viewPager = binding.viewPager
        viewPager.adapter = FilmSliderAdapter(FilmSliderAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewPagerTransform()

        viewModel.genresProperties.observe(viewLifecycleOwner, object : Observer<GenresResponse> {
            override fun onChanged(data: GenresResponse?) {
                data ?: return

                val genresGroup = binding.chipGroup

                val children = data.genres!!.map { genresName ->
                    val chip = LayoutInflater.from(genresGroup.context)
                        .inflate(R.layout.genre, genresGroup, false) as Chip
                    chip.text = genresName.genre
                    chip
                }

                genresGroup.removeAllViews()

                for (chip in children) {
                    genresGroup.addView(chip)
                }
            }
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(FilmFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
                viewModel.navigateToSelectedProperty.value?.filmId

            }
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun viewPagerTransform() {
        viewPager.offscreenPageLimit = 3
        viewPager.setPadding(100, 0, 100, 0)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        viewPager.setPageTransformer(compositePageTransformer)
    }
}