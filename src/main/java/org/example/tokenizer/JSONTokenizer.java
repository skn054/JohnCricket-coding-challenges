package org.example.tokenizer;
import org.example.lexer.Lexer;
import org.example.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class JSONTokenizer implements Tokenizer<String,List<Token>>{

    private Lexer lexer;
    public JSONTokenizer(Lexer lexer){
        this.lexer = lexer;
    }

    @Override
    public List<Token> tokenize(String source) throws ParseException {




        return  lexer.findAllTokens(source);


//        return new Token(JSONTokens.TOKEN_NUMBER,"asss")
    }
}
