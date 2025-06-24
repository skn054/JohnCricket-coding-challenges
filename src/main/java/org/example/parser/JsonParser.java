package org.example.parser;

import org.example.tokenizer.JSONTokenizer;
import org.example.tokenizer.JSONTokens;
import org.example.tokenizer.Token;
import org.example.tokenizer.Tokenizer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser implements Parser<String, Map<String,Object>>{

    private final Tokenizer<String,List<Token>> tokenizer;
    public JsonParser(Tokenizer<String,List<Token>> tokenizer){
        this.tokenizer = tokenizer;
    }
    int pos;

    @Override
    public Map<String, Object> parse(String source) throws ParseException {
        this.pos = 0;
        List<Token> tokens= tokenizer.tokenize(source);
        for(Token token : tokens){
            System.out.println(token.toString());
        }
        if(tokens.isEmpty()){
            throw new ParseException("Cannot parse an empty string.");
        }
        if(peekCurrentToken(tokens)!=JSONTokens.TOKEN_OPEN_CURLYBRACKET){
            throw new ParseException("Root of JSON document must be an object (must start with '{').");
        }
        Map<String,Object> result = parseObject(tokens);
//        if(pos < tokens.size()){
//            throw new ParseException("Extra tokens found after the end of the JSON object.");
//        }
        return result;
//        return Map.of();
    }

    public Map<String, Object> parseObject(List<Token> tokens) throws ParseException {
        Map<String,Object> jsonObject = new HashMap<>();

        consumeToken(tokens,JSONTokens.TOKEN_OPEN_CURLYBRACKET);

        if(peekCurrentToken(tokens) == JSONTokens.TOKEN_CLOSE_CURLYBRACKET){
                consumeToken(tokens,JSONTokens.TOKEN_CLOSE_CURLYBRACKET);
                return jsonObject;
        }
        parseMember(tokens,jsonObject);
        consumeToken(tokens,JSONTokens.TOKEN_CLOSE_CURLYBRACKET);
        return jsonObject;

    }

    private void parseMember(List<Token> tokens,Map<String,Object> jsonObject) throws ParseException {
        parsePair(tokens,jsonObject);
        if(peekCurrentToken(tokens) == JSONTokens.TOKEN_COMMA){
            consumeToken(tokens,JSONTokens.TOKEN_COMMA);
            parseMember(tokens,jsonObject);
        }
    }

    private void parsePair(List<Token> tokens,Map<String,Object> jsonObject) throws ParseException {
        Token stringValue = consumeToken(tokens,JSONTokens.TOKEN_STRING);

        if(peekCurrentToken(tokens) == JSONTokens.TOKEN_COLON){
            consumeToken(tokens,JSONTokens.TOKEN_COLON);
            Object value = parseValue(tokens);
            jsonObject.put(stringValue.getText(),value);
        }else{
            throw new ParseException("Invalid JSON");
        }



    }

//    private void parseString(List<Token> tokens) throws ParseException {
//
//        consumeToken(tokens,JSONTokens.TOKEN_STRING);
//    }

    private Token consumeToken(List<Token> tokens,JSONTokens expectedType) throws ParseException {

        if (pos >= tokens.size()) {
            throw new ParseException("Unexpected end of input. Expected " + expectedType);
        }
        Token currentToken = tokens.get(pos);
        if(currentToken.getTokentype()!= expectedType){
            throw new ParseException("Syntax Error: Expected " + expectedType +
                    " but found " + currentToken.getTokentype() + " at position " + pos);
        }
        pos++;
        return currentToken;
    }


    private Object parseValue(List<Token> tokens) throws ParseException {
        Token currToken = tokens.get(pos);
        JSONTokens tokentype = currToken.getTokentype();
        switch (tokentype){
            case TOKEN_BOOLEAN -> {
                consumeToken(tokens,JSONTokens.TOKEN_BOOLEAN);
                return Boolean.parseBoolean(currToken.getText());
            }
            case TOKEN_NUMBER -> {
                consumeToken(tokens,JSONTokens.TOKEN_NUMBER);
                return new BigDecimal(currToken.getText());
            }
            case TOKEN_STRING -> {
                consumeToken(tokens,JSONTokens.TOKEN_STRING);
                return  currToken.getText();

                }
            case TOKEN_NULL -> {
                consumeToken(tokens,JSONTokens.TOKEN_NULL);
                return null;
            }
            case TOKEN_OPEN_CURLYBRACKET -> {

                return parseObject(tokens);
            }
            case TOKEN_OPEN_SQUAREBRACKET -> {

                return parseArray(tokens);
            }
            default -> {
                throw new ParseException("Invalid JSON");
            }

        }
    }

    private List<Object> parseArray(List<Token> tokens) throws ParseException {
        List<Object> list = new ArrayList<>();
        consumeToken(tokens,JSONTokens.TOKEN_OPEN_SQUAREBRACKET);
        if(peekCurrentToken(tokens) == JSONTokens.TOKEN_CLOSE_SQUAREBRACKET){
            consumeToken(tokens,JSONTokens.TOKEN_CLOSE_SQUAREBRACKET);
            return list;
        }
        parseElements(tokens,list);
        consumeToken(tokens,JSONTokens.TOKEN_CLOSE_SQUAREBRACKET);
        return list;

    }

    private void parseElements(List<Token> tokens,List<Object> list) throws ParseException {
        Object value = parseValue(tokens);
        if(peekCurrentToken(tokens) == JSONTokens.TOKEN_COMMA){
            consumeToken(tokens,JSONTokens.TOKEN_COMMA);
            parseElements(tokens,list);
        }
        list.add(value);

    }

    public void closeObject(List<Token> tokens) throws ParseException {

        consumeToken(tokens,JSONTokens.TOKEN_CLOSE_CURLYBRACKET);
    }
    public JSONTokens peekCurrentToken(List<Token> tokens){
        if(pos < tokens.size()){
            return tokens.get(pos).getTokentype();
        }else{
            return JSONTokens.TOKEN_EOF;
        }
    }

    public JSONTokens peekNextToken(List<Token> tokens){
        if(pos +1 < tokens.size()){
            return tokens.get(pos + 1).getTokentype();
        }else{
            return JSONTokens.TOKEN_EOF;
        }
    }
}
