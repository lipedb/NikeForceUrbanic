package com.kforce.urbanic.ui.search

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.kforce.urbanic.enums.DefinitionItemType
import androidx.core.view.children
import com.kforce.urbanix.R

/**
 * this class used to add border between Definition list items
 * @param context : [Context]
 */
class DefinitionItemDecoration(var context: Context, dividerColors: Pair<Int, Int>) : ItemDecoration() {
    private val definitionHeaderDividerHeight: Int by lazy {
        context.resources.getDimension(R.dimen.definitionHeaderDividerHeight)
            .toInt()
    }
    private val definitionItemDividerHeight: Int by lazy {
        context.resources.getDimension(R.dimen.definitionItemDividerHeight)
            .toInt()
    }
    private val definitionItemHorizontalPadding: Int by lazy {
        context.resources.getDimension(R.dimen.definition_list_item_horizontal_padding)
            .toInt()
    }
    private val dividerPaintThick: Paint by lazy {
        val paint = Paint()
        paint.color = dividerColors.first
        paint
    }
    private val dividerPaintThin: Paint by lazy {
        val paint = Paint()
        paint.color = dividerColors.second
        paint
    }

    private var divider: Drawable? = null

    init {
        val styledAttributes = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = styledAttributes.getDrawable(0)
        styledAttributes.recycle()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (-1 >= position) return
        parent.adapter?.getItemViewType(position)?.let { viewType ->
            getItemViewType(viewType)?.let {
                when (it) {
                    DefinitionItemType.EXPANDABLE -> {
                        val nextDefinitionItemType = getNextItemViewType(position, parent)
                        if (nextDefinitionItemType == DefinitionItemType.HEADER) {
                            outRect.bottom = definitionHeaderDividerHeight
                        }
                    }
                    else -> outRect.setEmpty()
                }
                return
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (child in parent.children) {
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = parent.getChildAdapterPosition(child)

            /**
             * Draw a different divider for different situations
             * ie. after a regular list item
             * or if the next list item is a header
             */
            parent.adapter?.let { adapter ->
                divider?.let {
                    val viewType = adapter.getItemViewType(position)
                    val itemType = getItemViewType(viewType)
                    val nextItemType = getNextItemViewType(position, parent)

                    when (itemType) {
                        DefinitionItemType.EXPANDABLE -> {
                            var paint = dividerPaintThick
                            var left = 0
                            var right = parent.width
                            val top = child.bottom + params.bottomMargin
                            val dividerHeight = when (nextItemType) {
                                DefinitionItemType.EXPANDABLE -> {
                                    paint = dividerPaintThin
                                    left += definitionItemHorizontalPadding
                                    right -= definitionItemHorizontalPadding
                                    definitionItemDividerHeight
                                }
                                DefinitionItemType.HEADER -> definitionHeaderDividerHeight
                                else -> 0
                            }
                            val bottom = top + dividerHeight
                            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the DefinitionItemType for the given viewType
     *
     * @param viewType Int received from the adapter
     *
     * @return DefinitionItemType if there is a matching one for the viewType, otherwise null
     */
    private fun getItemViewType(viewType: Int): DefinitionItemType? {
        return try {
            DefinitionItemType.values()[viewType]
        } catch (e: Exception) {
            Log.v("Error","No DefinitionItemType found for viewType: $viewType")
            null
        }
    }

    /**
     * Get the next DefinitionItemType for the given adapter position
     *
     * @param position Int position in the recyclerview's adapter
     * @param recyclerView RecyclerView the recyclerview we need to calculate from
     *
     * @return DefinitionItemType? returns the next item's DefinitionItemType if available, otherwise null
     */
    private fun getNextItemViewType(position: Int, recyclerView: RecyclerView): DefinitionItemType? {
        val nextPosition = position + 1
        return recyclerView.adapter?.let {
            val nextViewType: Int? =
                if (nextPosition < it.itemCount) it.getItemViewType(
                    nextPosition
                ) else null
            nextViewType?.let { getItemViewType(nextViewType) }
        }
    }
}