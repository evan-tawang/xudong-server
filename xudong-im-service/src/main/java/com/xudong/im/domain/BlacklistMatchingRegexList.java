package com.xudong.im.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019-06-15
 */
public class BlacklistMatchingRegexList extends ArrayList<String> {

    private static final long serialVersionUID = 8683452581122892189L;

    public BlacklistMatchingRegexList() {
        super();
    }

    public BlacklistMatchingRegexList(int size) {
        super(size);
    }

    public BlacklistMatchingRegexList(List<String> list) {
        Collections.addAll(this, list.toArray(new String[]{}));
    }
}
