package com.gesvik.movieapp.filmOverview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FilmFragment(FragmentTitle.BOX_OFFICE)

            1 -> FilmFragment(FragmentTitle.TOP_FILMS)

            2 -> FilmFragment(FragmentTitle.SOON)

            else -> FilmFragment(FragmentTitle.BOX_OFFICE)
        }
    }

    object FragmentTitle {
        const val BOX_OFFICE = "В прокате"
        const val TOP_FILMS = "Топ фильмов"
        const val SOON = "Скоро"
    }
}