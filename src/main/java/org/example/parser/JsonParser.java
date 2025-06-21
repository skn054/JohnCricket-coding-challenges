package org.example.parser;

import org.example.tokenizer.JSONTokenizer;
import org.example.tokenizer.Token;
import org.example.tokenizer.Tokenizer;

import java.util.List;
import java.util.Map;

public class JsonParser implements Parser<String, Map<String,Object>>{

    private final Tokenizer<String,List<Token>> tokenizer;
    public JsonParser(Tokenizer<String,List<Token>> tokenizer){
        this.tokenizer = tokenizer;
    }


    @Override
    public Map<String, Object> parse(String source) throws ParseException {

        List<Token> tokens= tokenizer.tokenize(source);
        return Map.of();
    }
}
