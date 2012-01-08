/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import java.util.List;

/**
 *
 * @author Administrator
 */
public class NullTransformer implements SentenceTransformer{

    @Override
    public List<String> transform(List<String> words) {
        return words;
    }

}
