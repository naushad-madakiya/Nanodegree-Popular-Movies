package com.nanodegree.popularmovies.common;

/**
 * Created by naushad on 20/5/16.
 */
public enum SortingType {
    POPULAR("popular"),
    TOP_RATED("top_rated");

    private final String text;

    /**
     * @param text
     */
    SortingType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
