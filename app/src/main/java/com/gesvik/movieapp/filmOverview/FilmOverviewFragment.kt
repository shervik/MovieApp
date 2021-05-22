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
import com.gesvik.movieapp.databinding.FilmOverviewFragmentBinding
import com.gesvik.movieapp.filmTabs.TabsFragmentDirections
import com.gesvik.movieapp.network.entities.GenresResponse
import com.google.android.material.chip.Chip
import timber.log.Timber
import kotlin.math.abs

class FilmOverviewFragment(private val category: String?) : Fragment() {
    private lateinit var binding: FilmOverviewFragmentBinding

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView")

        binding = FilmOverviewFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated")

        val categoryName = category

        val viewModelFactory = ViewModelFactory(categoryName!!)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(FilmsViewModel::class.java)
        binding.viewModel = viewModel

        viewPager = binding.viewPager
        val filmSliderAdapter = FilmSliderAdapter(FilmSliderAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })
        viewPager.adapter = filmSliderAdapter
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

        viewModel.properties.observe(viewLifecycleOwner, Observer {
            it.let {
                filmSliderAdapter.submitList(it.films)
            }
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.requireParentFragment().findNavController().navigate(TabsFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
                viewModel.navigateToSelectedProperty.value?.filmId

            }
        })

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