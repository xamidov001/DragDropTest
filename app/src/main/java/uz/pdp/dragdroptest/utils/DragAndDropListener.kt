package uz.pdp.dragdroptest.utils

import android.content.ClipData
import android.content.ClipDescription
import android.view.DragEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import uz.pdp.dragdroptest.Item
import uz.pdp.dragdroptest.adapter.MyRecyclerAdapter
import kotlin.math.abs

class DragAndDropListener(val b: Boolean) : View.OnDragListener {
    override fun onDrag(view: View, event: DragEvent): Boolean {
        return when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                view as RecyclerView
                val adapter = view.adapter as MyRecyclerAdapter
                val x = event.x
                val width = view.width
                if (abs(width-x)<width/20) {
                    val currentItem = adapter.viewPager2?.currentItem
                    if (currentItem == 0) {
                        adapter.changeView(1)
                    }
                } else if (x < width/20) {
                    val currentItem = adapter.viewPager2?.currentItem
                    if (currentItem == 1) {
                        adapter.changeView(0)
                    }
                }
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                // Gets the item containing the dragged data.
                val item: ClipData.Item = event.clipData.getItemAt(0)

                // Gets the text data from the item.
                val dragData = item.text.toString()

                // Displays a message containing the dragged data.
                Toast.makeText(view.context, "Dragged data is $dragData", Toast.LENGTH_LONG).show()

                view as RecyclerView
                val adapter = view.adapter as MyRecyclerAdapter
                val adapterPosition = view.findContainingViewHolder(
                    view.findChildViewUnder(
                        event.x,
                        event.y
                    )!!
                )?.adapterPosition
                adapter.addItem(adapterPosition?:0, Item(dragData, true))

                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }


}