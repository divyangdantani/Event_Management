package com.More_Item;

import android.view.View;
import android.widget.TextView;

import com.example.divyangdantani.eventmanagementfinal.R;

public class MoreViewHolder extends ChildViewHolder {

    private TextView mMoviesTextView;

    public MoreViewHolder(View itemView) {
        super(itemView);
        mMoviesTextView = itemView.findViewById(R.id.tv_movies);
    }

    public void bind(More more) {
        mMoviesTextView.setText(more.getName());
    }
}
