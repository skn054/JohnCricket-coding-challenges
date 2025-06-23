package org.example.lexer;

import org.example.parser.ParseException;
import org.example.tokenizer.JSONTokens;
import org.example.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private int pos;
    private String currToken;
    ArrayList<Token> tokens;
    private JSONTokens jsonTokens;
    public Lexer(int pos,String currToken){
        this.pos = pos;
        this.currToken = currToken;
        tokens =new ArrayList<>();

    }

    public boolean isToken(String source) throws ParseException {
        if(source.contentEquals("{")){
            jsonTokens = JSONTokens.TOKEN_OPEN_CURLYBRACKET;
            return true;
        }else if(source.contentEquals("}")){
            jsonTokens = JSONTokens.TOKEN_CLOSE_CURLYBRACKET;
            return true;
        }else if(source.contentEquals("[")){
            jsonTokens = JSONTokens.TOKEN_OPEN_SQUAREBRACKET;
            return  true;
        }else if(source.contentEquals("]")){
            jsonTokens = JSONTokens.TOKEN_CLOSE_SQUAREBRACKET;
            return  true;
        }else if(source.matches("[0-9]")){
            jsonTokens = JSONTokens.TOKEN_NUMBER;
            return  true;
        }else if(source.matches("true|false")){
            jsonTokens = JSONTokens.TOKEN_BOOLEAN;
            return true;
        }else if(source.matches("null")){
            jsonTokens = JSONTokens.TOKEN_BOOLEAN;
            return true;
        }else{
            throw new ParseException("Invalid JSON string");


        }

    }
    public Token findNextToken(String source,int indexPos) throws ParseException {
          while(!isToken(source.substring(pos,indexPos))){
              indexPos++;
          }
          pos = indexPos;
          return new Token(jsonTokens,source.substring(pos,indexPos));
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
          int indexpos = 0;
          while(pos<source.length()){
              Token token = findNextToken(source,indexpos);
              tokens.add(token);
          }
          return tokens;
    }

}
