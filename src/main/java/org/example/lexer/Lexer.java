package org.example.lexer;

import org.example.parser.ParseException;
import org.example.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private int pos;
    private String currToken;
    ArrayList<Token> tokens;
    public Lexer(int pos,String currToken){
        this.pos = pos;
        this.currToken = currToken;
        tokens =new ArrayList<>();

    }

    public Token findNextToken(String source){

    }

    public List<Token> findAllTokens(String source) throws ParseException {
        /**
         * go through string one char at a time and find next Token,
         * add that token to final list of tokens
         */

          if(source == null){
              throw new ParseException("JSON object is null");
          }
          if(source.trim().isEmpty()){
              throw  new ParseException("JSON object is empty");
          }
    }

}
