package com.example.moviesapplication.ui

import android.annotation.SuppressLint
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapplication.R


class RecyclerViewAdapter(
    private var data: List<MoviesUi>,
    private var onItemClick: (String) -> Unit
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = data[position]
        holder.bind(movie)
    }
    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<MoviesUi>){
        data = newData
        notifyDataSetChanged()
    }
    //TODO
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMovie: ImageView = itemView.findViewById(R.id.imgMovie)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtYear: TextView = itemView.findViewById(R.id.txtYear)

        fun bind(movie: MoviesUi) {
            txtTitle.text = movie.text
            txtYear.text = movie.year

            Glide.with(itemView)
                .load(movie.url)
                .into(imgMovie)

            itemView.setOnClickListener {
                onItemClick.invoke(movie.id)
            }
        }
    }


}