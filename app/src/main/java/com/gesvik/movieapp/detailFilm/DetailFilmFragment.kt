package com.gesvik.movieapp.detailFilm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gesvik.movieapp.R
import com.gesvik.movieapp.databinding.DetailFilmFragmentBinding
import com.gesvik.movieapp.network.entities.FilmsItem
import com.google.android.material.chip.Chip

class DetailFilmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val application = requireNotNull(activity).application
        val binding = DetailFilmFragmentBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        val filmsProperty = DetailFilmFragmentArgs.fromBundle(requireArguments()).selectedProperty
        val viewModelFactory = DetailViewModelFactory(filmsProperty, application)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailFilmViewModel::class.java)
        binding.viewModel = viewModel

        binding.recyclerViewCast.adapter = StaffAdapter()

        viewModel.selectedProperty.observe(viewLifecycleOwner, object : Observer<FilmsItem> {
            override fun onChanged(data: FilmsItem?) {
                data ?: return

                val genresGroup = binding.genresGroup

                val children = data.genres.map { genresName->
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

        viewModel.filmItemProperty.observe(viewLifecycleOwner,
            { t ->
                if (t.data.description.isNullOrEmpty()) {
                    binding.titleSummary.visibility = View.GONE
                    binding.textSummary.visibility = View.GONE
                }
            })

        return binding.root
    }
}