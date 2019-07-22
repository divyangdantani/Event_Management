package com.More_Item;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.divyangdantani.eventmanagementfinal.R;

import java.util.Arrays;
import java.util.List;

public class Fragment_More extends Fragment{

    private MoreCategoryAdapter mAdapter;
    private RecyclerView recyclerView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_more, container, false);

        More movie_one = new More("Society Name");
        More movie_two  = new More("Details about society");
        More movie_three = new More("Society Fund");
        More movie_four  = new More("Society Volunteer");
        More movie_five = new More("How to Register? ");
        More movie_six = new More("How to Login?");
        More movie_seven = new More("How to Register Event?");
        More movie_eight = new More("Delete my Account");
        More movie_nine = new More("How to add more family member?");
        More movie_ten = new More("About Developer");
        More movie_eleven = new More("About Company");
        More movie_tweleve = new More("1.1.0");

        MoreCategory molvie_category_one = new MoreCategory("About Society", Arrays.asList(movie_one, movie_two, movie_three, movie_four));
        MoreCategory molvie_category_two = new MoreCategory("Help Desk", Arrays.asList(movie_five, movie_six, movie_seven,movie_eight,movie_nine));
        MoreCategory molvie_category_three = new MoreCategory("About us", Arrays.asList(movie_ten, movie_eleven));
        MoreCategory molvie_category_four = new MoreCategory("Version", Arrays.asList(movie_tweleve));

        final List<MoreCategory> movieCategories = Arrays.asList(molvie_category_one,  molvie_category_two, molvie_category_three,molvie_category_four);

        recyclerView =  view.findViewById(R.id.recyclerview);
        mAdapter = new MoreCategoryAdapter(getActivity(), movieCategories);
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                MoreCategory expandedMovieCategory = movieCategories.get(position);

                String toastMsg = getResources().getString(R.string.expanded, expandedMovieCategory.getName());
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onListItemCollapsed(int position) {
                MoreCategory collapsedMovieCategory = movieCategories.get(position);

                String toastMsg = getResources().getString(R.string.collapsed, collapsedMovieCategory.getName());
                Toast.makeText(getActivity(),
                        toastMsg,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        return view;

    }

}

