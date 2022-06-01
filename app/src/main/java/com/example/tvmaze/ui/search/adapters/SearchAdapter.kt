package com.example.tvmaze.ui.search.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvmaze.R
import com.example.tvmaze.data.model.Show
import com.example.tvmaze.databinding.ShowItemLayoutBinding

class SearchAdapter (
    private val listener: (Show) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var mDataSet: List<Show> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_item_layout, parent, false)

        mContext = parent.context
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val show = mDataSet[position]
        holder.itemView.setOnClickListener {
            listener(show)
        }
        holder.bind(show)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = mDataSet.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val binding = ShowItemLayoutBinding.bind(view)

        fun bind(item: Show) = with(binding){
            item.apply {
                Glide.with(mContext)
                    .load(item.poster ?: mContext.resources.getDrawable(R.drawable.res_no_image, null))
                    .centerInside()
                    .into(image)

                showName.text = item.name
            }
        }
    }
}