package com.example.tvmaze.ui.show_details

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmaze.R
import com.example.tvmaze.data.model.Episode


class CustomSeasonEpisodeView: ConstraintLayout {

    private lateinit var mAdapter: EpisodeAdapter
    private val TAG = CustomSeasonEpisodeView::class.simpleName

    private lateinit var mSeason: String
    private lateinit var mEpisodes: List<Episode>
    
    private lateinit var mTitle: TextView
    private lateinit var mEpisodesRecyclerView: RecyclerView

    private lateinit var mRootView: View
    private lateinit var mListener: (Episode)-> Unit

    constructor(context: Context, season: Int, episodes: List<Episode>, listener: (Episode)-> Unit): super(context) {
        mListener = listener
        mSeason = season.toString()
        mEpisodes = episodes
        initView()
    }

    constructor(context: Context, attr: AttributeSet? = null): super(context, attr) {
        initView()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ):   super(context, attrs, defStyleAttr) {
        initView()
    }

    /**
     * init View Here
     */
    private fun initView() {
        
        mRootView = (context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.season_episodes_custom_layout, this)

        mTitle = mRootView.findViewById(R.id.title) as TextView
        mTitle.text = "Season $mSeason"

        mEpisodesRecyclerView = mRootView.findViewById(R.id.recyclerView) as RecyclerView
        mEpisodesRecyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL,false)
        mAdapter = EpisodeAdapter(::onClickListener)
        mAdapter.mDataSet = mEpisodes

        mEpisodesRecyclerView.adapter = mAdapter
      /*  mEpisodesRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(2,
                resources.getDimensionPixelSize(R.dimen.recycler_item_width)
            )
        )*/
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.vertical_divider,
                null
            )!!
        )
        mEpisodesRecyclerView.addItemDecoration(
            itemDecorator
        )
    }

    private fun onClickListener(episode: Episode) {
        mListener(episode)
    }

}