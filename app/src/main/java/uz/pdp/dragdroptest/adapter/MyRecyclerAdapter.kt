package uz.pdp.dragdroptest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import uz.pdp.dragdroptest.Item
import uz.pdp.dragdroptest.databinding.ItemBigBinding

class MyRecyclerAdapter(var list: ArrayList<Item>) :
    RecyclerView.Adapter<MyRecyclerAdapter.VH>() {

    private lateinit var longClick: (n: Int, v: View) -> Unit
    var viewPager2: ViewPager2? = null


    override fun getItemCount() = list.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = (ItemBigBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    fun addItem(position: Int, newItem: Item) {
        if (list[position].visibility) {
            list.add(position, newItem)
            notifyItemInserted(position)
        } else {
            list[position] = newItem
            notifyItemChanged(position)
        }
    }

    fun deleteItem(position: Int) {
        list[position] = Item()
        notifyItemChanged(position)
    }

    fun changeView(int: Int) {
        if (viewPager2 != null) {
            viewPager2?.currentItem = int
        }
    }


    fun clickLong(c: (Int, View) -> Unit) {
        longClick = c
    }

    inner class VH(val binding: ItemBigBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Item) {
            binding.apply {

                root.visibility = if (item.visibility) View.VISIBLE else View.INVISIBLE

                if (item.visibility) {
                    tvName.text = item.name
                    root.setOnLongClickListener {
                        longClick.invoke(position, it)
                        true
                    }
                }
            }
        }
    }
}