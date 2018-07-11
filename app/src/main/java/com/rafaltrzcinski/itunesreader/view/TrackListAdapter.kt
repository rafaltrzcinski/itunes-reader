package com.rafaltrzcinski.itunesreader.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rafaltrzcinski.itunesreader.R
import com.rafaltrzcinski.itunesreader.domain.model.Track
import com.rafaltrzcinski.itunesreader.domain.model.Track.LocalTrack
import com.rafaltrzcinski.itunesreader.domain.model.Track.RemoteTrack
import com.rafaltrzcinski.itunesreader.utils.DateFormat
import kotlinx.android.synthetic.main.item_track.view.*


class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    private val items = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackListAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun loadItems(items: List<Track>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(track: Track) {
            when (track) {
                is LocalTrack -> bindLocalTrack(track)
                is RemoteTrack -> bindRemoteTrack(track)
            }
        }

        private fun bindLocalTrack(track: LocalTrack) {
            itemView.artistName.text = track.artistName
            itemView.songTitle.text = track.songTitle
            itemView.releaseYear.text =
                    if (track.releaseYear == 0) "Unknown year"
                    else "${track.releaseYear}"
        }

        private fun bindRemoteTrack(track: RemoteTrack) {
            itemView.artistName.text = track.artistName
            itemView.songTitle.text = track.trackName
            itemView.releaseYear.text = "${DateFormat.getYearFromDateString(track.releaseDate)}"
        }
    }
}