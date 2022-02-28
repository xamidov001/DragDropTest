package uz.pdp.dragdroptest.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.dragdroptest.Item
import uz.pdp.dragdroptest.R
import uz.pdp.dragdroptest.adapter.MyRecyclerAdapter
import uz.pdp.dragdroptest.databinding.FragmentItemBinding
import uz.pdp.dragdroptest.utils.DragAndDropListener

class ItemFragment : Fragment(R.layout.fragment_item) {

    private val binding by viewBinding(FragmentItemBinding::bind)
    private var list = ArrayList<Item>()
    private val recyclerAdapter by lazy { MyRecyclerAdapter(list) }
    private var boolean: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            boolean = it.getBoolean("rec")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            if (list.isEmpty()) {
                list = generateList()
                recyclerAdapter.list = list
                recyclerAdapter.notifyDataSetChanged()
            }

            recyclerAdapter.viewPager2 =
                (activity as AppCompatActivity).findViewById(R.id.viewPager)
            recycleView.adapter = recyclerAdapter
            recyclerAdapter.clickLong { position, view ->
                startDrag(view, position)
            }

            recycleView.setOnDragListener(DragAndDropListener(false))

        }
    }

    private fun generateList(): ArrayList<Item> {
        list.add(Item("BusinessClub", true))
        for (i in 1..22) {
            list.add(Item())
        }
        list.add(Item("Tezkor", true))
        return list
    }


    private fun startDrag(view: View?, position: Int): Boolean {
        view?.let {
            val tag = list[position].name
            val item = ClipData.Item(tag)
            val data = ClipData(tag, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
            val shadow = View.DragShadowBuilder(it)
            recyclerAdapter.deleteItem(position)

            if (Build.VERSION.SDK_INT >= 24) {
                it.startDragAndDrop(data, shadow, it, 0)
            } else {
                it.startDrag(data, shadow, it, 0)
            }
            return true
        } ?: return false

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Boolean) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    this.putBoolean("rec", param1)
                }
            }
    }
}