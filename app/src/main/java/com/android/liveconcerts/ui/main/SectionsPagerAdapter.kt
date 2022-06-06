package com.android.liveconcerts.ui.main

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.liveconcerts.R
import com.android.liveconcerts.fragments.ArtistFragment
import com.android.liveconcerts.fragments.EventsFragment
import com.android.liveconcerts.fragments.MainFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> return EventsFragment() //.newInstance(0);
            1 -> return ArtistFragment() //.newInstance(1);
            2 -> return MainFragment() //.newInstance(2);
            else -> { throw Resources.NotFoundException("No se encuentra la posición")}
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}