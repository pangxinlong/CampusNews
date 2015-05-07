package com.campusnews.adapter;

public interface BasePagerAdapter {
	 /**
     * Get icon representing the page at {@code index} in the adapter.
     */
    int getIconResId(int index);

    // From PagerAdapter
    int getCount();
}
