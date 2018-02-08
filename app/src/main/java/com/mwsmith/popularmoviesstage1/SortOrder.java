package com.mwsmith.popularmoviesstage1;

/**
 * Created by Matthew Smith on 29/01/2018.
 */

public enum SortOrder {
    POPULARITY(R.id.menu_popular, "popular"), TOP_RATED(R.id.menu_top_rated, "top_rated");

    private final int mMenuId;
    private final String mUrlString;

    SortOrder(int menu_id, String urlString) {
        this.mMenuId = menu_id;
        this.mUrlString = urlString;
    }

    private  int getMenuId() {
        return mMenuId;
    }

    public String getUrlString() {
        return mUrlString;
    }

    public static SortOrder getSortOrderFromMenuOption(int id) {
        for (SortOrder sortOrder : values()) {
            if (sortOrder.getMenuId() == id) {
                return sortOrder;
            }
        }
        return null;
    }
}
