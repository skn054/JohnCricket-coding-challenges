package org.example.parser;

/**
 *  A generic interface for components that parse a source of data into a
 *  * structured result.
 *
 * @param <S> The type of the source data to parse (e.g., String, InputStream).
 * @param <R> The type of the result object after parsing.
 *
 *
 *  */

public interface Parser<S,R> {

    /**
     *
     * @param source source The source data to be parsed.
     * @return The structured result of the parsing operation.
     */
    R parse(S source) throws  ParseException;
}
