package com.crowd.util;

import com.crowd.model.Word;

/**
 * Created by marce on 3/21/18.
 */
public class Validations {

    public static boolean isDataValid(Word word) {
        return (null != word.getNombre() && word.getNombre().length() < 26);
    }
}
