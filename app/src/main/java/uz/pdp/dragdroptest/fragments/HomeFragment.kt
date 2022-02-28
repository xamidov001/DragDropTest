package uz.pdp.dragdroptest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.dragdroptest.R
import uz.pdp.dragdroptest.adapter.ViewAdapter
import uz.pdp.dragdroptest.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val adapter by lazy { ViewAdapter(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewPager.adapter = adapter
        }
    }

}