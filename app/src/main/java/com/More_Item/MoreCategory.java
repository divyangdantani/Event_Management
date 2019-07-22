package com.More_Item;

import java.util.List;

public class MoreCategory implements ParentListItem {
    private String mName;
    private List<More> mMovies;

    public MoreCategory(String name, List<More> movies) {
        mName = name;
        mMovies = movies;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<?> getChildItemList() {
        return mMovies;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
