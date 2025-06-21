package org.example.tokenizer;

import org.example.parser.ParseException;

/**
 *
 * @param <S> source The type of the source data to tokenize
 * @param <R> The type of the result object after tokenizing.
 */
public interface Tokenizer<S,R> {

    public R tokenize(S source) throws ParseException;
}
