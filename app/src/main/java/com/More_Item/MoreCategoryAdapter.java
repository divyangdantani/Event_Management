package com.More_Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.divyangdantani.eventmanagementfinal.R;

import java.util.List;

public class MoreCategoryAdapter extends ExpandableRecyclerAdapter<MoreCategoryViewHolder, MoreViewHolder> {

    private LayoutInflater mInflator;

    public MoreCategoryAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
    }

    @Override
    public MoreCategoryViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View movieCategoryView = mInflator.inflate(R.layout.more_category_view, parentViewGroup, false);
        return new MoreCategoryViewHolder(movieCategoryView);
    }

    @Override
    public MoreViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View moviesView = mInflator.inflate(R.layout.more_view, childViewGroup, false);
        return new MoreViewHolder(moviesView);
    }

    @Override
    public void onBindParentViewHolder(MoreCategoryViewHolder moreCategoryViewHolder, int position, ParentListItem parentListItem) {
        MoreCategory moreCategory = (MoreCategory) parentListItem;
        moreCategoryViewHolder.bind(moreCategory);
    }

    @Override
    public void onBindChildViewHolder(MoreViewHolder moreViewHolder, int position, Object childListItem) {
        More more = (More) childListItem;
        moreViewHolder.bind(more);
    }
}
