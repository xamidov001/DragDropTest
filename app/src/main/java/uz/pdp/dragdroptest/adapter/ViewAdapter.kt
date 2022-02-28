package uz.pdp.dragdroptest.adapter

import androidx.constraintlayout.utils.widget.ImageFilterButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.dragdroptest.fragments.ItemFragment

class ViewAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private var currentPosition = -1

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        currentPosition = position
        return when(position) {
            0 -> ItemFragment.newInstance(true)
            1 -> ItemFragment.newInstance(false)
            else -> ItemFragment.newInstance(true)
        }
    }

    fun getPosition(): Int {
        return if (currentPosition > -1) currentPosition
        else 0
    }

}