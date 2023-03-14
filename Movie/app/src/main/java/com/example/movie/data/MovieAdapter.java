package com.example.movie.data;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie.R;
import com.example.movie.activities.MovieActivity;
import com.example.movie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;
        String currentDataUrl;
        int stepAnim = 0;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.posterImageItem);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startAnimate(itemView);
        }

        private void startAnimate(View view){
            Handler handler = new Handler();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    if(stepAnim == 0) {
                        view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100);

                    }
                    if(stepAnim == 1) {
                        view.animate().scaleX(1).scaleY(1).setDuration(50);
                        stepAnim = 0;
                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("dataUrl", currentDataUrl);
                        context.startActivity(intent);
                        return;
                    }

                    stepAnim ++;
                    handler.postDelayed(this,150);
                }
            };
            handler.post(runnable);
        }

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);

        String title = currentMovie.getTitle();
        String year = currentMovie.getYear();
        String poster = currentMovie.getPosterUrl();

        holder.currentDataUrl = currentMovie.getDataUrl();
        holder.titleTextView.setText(title);
        holder.yearTextView.setText(year);

        if (poster.length() < 10 ) {
            holder.posterImageView.setImageResource(R.drawable.baseline_hide_image_24);
        }else{
            Picasso.get().load(poster).fit().centerInside().into(holder.posterImageView);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}
