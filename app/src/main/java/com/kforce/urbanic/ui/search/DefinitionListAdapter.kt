package com.kforce.urbanic.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.library.baseAdapters.BR
import com.kforce.urbanic.enums.DefinitionItemType
import com.kforce.urbanic.ui.entity.DefinitionExpandableItem
import com.kforce.urbanic.ui.entity.DefinitionHeaderItem
import com.kforce.urbanic.ui.entity.DefinitionItem
import com.kforce.urbanix.R
import com.kforce.urbanix.databinding.FragmentDefinitionExpandableItemBinding
import com.kforce.urbanix.databinding.FragmentDefinitionHeaderItemBinding

/**
 * RecyclerView adapter implementation for the Definitions list
 */
class DefinitionListAdapter : RecyclerView.Adapter<DefinitionListAdapter.DefinitionsItemViewHolder>() {

    private var definitionsList: List<DefinitionItem> = emptyList()

    private var onDefinitionExpandableItemClick: ((DefinitionExpandableItem) -> Unit)? = null

    /**
     * Update the add triggers list value
     *
     * @param definitionsList List<DefinitionItem> the list of Definitions items
     */
    fun setList(definitionsList: List<DefinitionItem>) {
        this.definitionsList = definitionsList
        notifyDataSetChanged()
    }

    /**
     * Set the item click listener for DefinitionExpandable items
     *
     * @param onDefinitionExpandableItemClickListener ((DefinitionExpandableItem) -> Unit)?
     *  function to execute when clicking on an DefinitionExpandable item
     */
    fun setOnDefinitionExpandableItemClickListener(onDefinitionExpandableItemClickListener: ((DefinitionExpandableItem) -> Unit)?) {
        onDefinitionExpandableItemClick = onDefinitionExpandableItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (DefinitionItemType.values()[viewType]) {
            DefinitionItemType.EXPANDABLE -> DefinitionsExpandableItemViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.fragment_definition_expandable_item,
                    parent,
                    false
                )
            )
            DefinitionItemType.HEADER -> DefinitionsHeaderItemViewHolder(
                DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.fragment_definition_header_item,
                    parent,
                    false
                )
            )
        }.apply {
            onViewHolderCreated(this, viewType, binding)
        }
    }

    override fun onBindViewHolder(holder: DefinitionsItemViewHolder, position: Int) {
        holder.bind(definitionsList[position])
    }

    override fun getItemCount(): Int = definitionsList.size

    override fun getItemViewType(position: Int): Int {
        return when (definitionsList[position]) {
            is DefinitionExpandableItem -> DefinitionItemType.EXPANDABLE.ordinal
            is DefinitionHeaderItem -> DefinitionItemType.HEADER.ordinal
        }
    }

    /**
     * Gets the AddTriggerItemType for the corresponding viewType
     *
     * @param viewType Int view holder's type
     *
     * @return AddTriggerItemType returns AddTriggerItemType for the corresponding viewType
     */
    private fun getDefinitionItemType(viewType: Int): DefinitionItemType {
        return DefinitionItemType.values()[viewType]
    }

    // ----- ViewHolders ----- //
    /**
     * abstract class that provides structure for all sub DefinitionsItemViewHolder classes
     */
    abstract class DefinitionsItemViewHolder(open val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: DefinitionItem)
    }

    class DefinitionsHeaderItemViewHolder(override val binding: FragmentDefinitionHeaderItemBinding) : DefinitionsItemViewHolder(binding) {
        override fun bind(item: DefinitionItem) {
            item as DefinitionHeaderItem
            binding.definitionExpandableTitle.text = item.title
            binding.setVariable(BR.definitionHeader, item)
            binding.executePendingBindings()
        }
    }

    class DefinitionsExpandableItemViewHolder(override val binding: FragmentDefinitionExpandableItemBinding) : DefinitionsItemViewHolder(binding) {
        override fun bind(item: DefinitionItem) {
            item as DefinitionExpandableItem
            binding.definitionExpandableTitle.text = item.title
            binding.setVariable(BR.definitionExpandable, item)
            binding.executePendingBindings()
        }
    }

    /**
     *  Adds the on click listeners to the view holders
     */
    private fun onViewHolderCreated(
        viewHolder: DefinitionsItemViewHolder,
        viewType: Int,
        binding: ViewDataBinding
    ) {
        when (getDefinitionItemType(viewType)) {
            DefinitionItemType.EXPANDABLE -> {
                binding.root.setOnClickListener {
                    val definitionExpandableItem = (definitionsList[viewHolder.adapterPosition] as DefinitionExpandableItem)
                    onDefinitionExpandableItemClick?.invoke(definitionExpandableItem)
                }
            }
        }
    }
}
