package com.crowd.util;

import com.crowd.model.Word;

import java.util.Comparator;

/**
 * Created by marce on 3/22/18.
 */
public class CustomComparatorWord implements Comparator<Word> {

    private boolean sortDesc;

    public CustomComparatorWord(boolean sortDesc) {
        this.sortDesc = sortDesc;
    }

    @Override
    public int compare(Word o1, Word o2) {
        if (sortDesc) {
            return o1.getNombre().compareTo(o2.getNombre());
        }
        return o2.getNombre().compareTo(o1.getNombre());
    }
}
