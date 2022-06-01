package com.example.tvmaze.ui.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmaze.R
import com.example.tvmaze.data.model.HistorySearch
import com.example.tvmaze.databinding.HistorySearchItemLayoutBinding

class HistorySearchAdapter(
private val onHistorySearchItemClickListener: (String) -> Unit,
private val removeHistorySearch: (HistorySearch) -> Unit
) : RecyclerView.Adapter<HistorySearchAdapter.ViewHolder>() {

    var mDataSet: MutableList<HistorySearch> = arrayListOf()
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_search_item_layout, parent, false)

        mContext = parent.context
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val search = mDataSet[position]
        holder.itemView.setOnClickListener {
            onHistorySearchItemClickListener(search.search)
        }
        holder.bind(search, removeHistorySearch )
    }

    fun remove(historySearch: HistorySearch) : Int {
        val index = mDataSet.indexOf(historySearch)
        mDataSet.removeAt(index)
        notifyItemRemoved(index)

        return mDataSet.size
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mDataSet.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = HistorySearchItemLayoutBinding.bind(view)

        fun bind(item: HistorySearch, removeHistorySearch: (HistorySearch) -> Unit) = with(binding){
            item.apply {
                criteria.text  = item.search
            }

            delete.setOnClickListener {
                removeHistorySearch(item)
            }
        }
    }
}