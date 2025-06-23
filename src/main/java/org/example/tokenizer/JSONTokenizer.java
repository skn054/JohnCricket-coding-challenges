package org.example.tokenizer;
import org.example.lexer.Lexer;
import org.example.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class JSONTokenizer implements Tokenizer<String,List<Token>>{

    private Lexer lexer;

    private int pos;
    private String currToken;
    ArrayList<Token> tokens;
    private JSONTokens jsonTokens;


    public JSONTokenizer(){
        this.pos = 0;
        tokens =new ArrayList<>();
    }


    @Override
    public List<Token> tokenize(String source) throws ParseException {

        if(source == null){
            throw new ParseException("JSON object is null");
        }
        if(source.trim().isEmpty()){
            throw  new ParseException("JSON object is empty");
        }


       while (pos < source.length()){
           char currChar = source.charAt(pos);

           if (currChar == '{') {
               tokens.add(new Token(JSONTokens.TOKEN_OPEN_CURLYBRACKET, "{"));
               pos++; // Consume the character
           }else if(currChar == '}'){
               tokens.add(new Token(JSONTokens.TOKEN_CLOSE_CURLYBRACKET, "}"));
               pos++;
           }else if(currChar == '['){
               tokens.add(new Token(JSONTokens.TOKEN_OPEN_SQUAREBRACKET, "["));
               pos++;
           }else if(currChar == ']'){
               tokens.add(new Token(JSONTokens.TOKEN_CLOSE_SQUAREBRACKET, "]"));
               pos++;
           }else if(currChar == ','){
               tokens.add(new Token(JSONTokens.TOKEN_COMMA, ","));
               pos++;
           }else if(currChar == ':'){
               tokens.add(new Token(JSONTokens.TOKEN_COLON,":"));
               pos++;
           }else if(currChar == '"'){
               String res = getStringToken(source,pos);
               tokens.add(new Token(JSONTokens.TOKEN_STRING,res));
               int currLength = res.length();
               pos=pos + currLength;
           }else if(Character.isWhitespace(currChar)){
               pos++;
           }else if(Character.isDigit(currChar)){
                //check if token is a digit
               String res = getDIgit(source,pos);
               tokens.add(new Token(JSONTokens.TOKEN_NUMBER,res));
               pos = pos + res.length();
           }else if(currChar == 't' || currChar =='f' || currChar == 'n'){
               // This is a literal (true, false, null). Needs a helper.
               boolean res = source.substring(pos,pos+4).matches("true|false|null");

               if(res){
                   String subString = source.substring(pos,pos+4);
                   if(subString.contentEquals("null")){
                       tokens.add(new Token(JSONTokens.TOKEN_NULL,source.substring(pos,pos+4)));
                   }else{
                       tokens.add(new Token(JSONTokens.TOKEN_BOOLEAN,source.substring(pos,pos+4)));
                   }


               }else{
                   throw new ParseException("Invalid Exception");
               }
               pos = pos + 4;


           }else{
               throw new ParseException("Inavlid character found at position" + pos);
           }
//           pos++;
       }

          return tokens;
//        return new Token(JSONTokens.TOKEN_NUMBER,"asss")
    }

    private String getStringToken(String source, int pos) {
        int currPos = pos+1;
        char currChar = source.charAt(currPos);
        int strLength = source.length();
        while(currPos < strLength && currChar!='"' ){
            currPos++;
            currChar = source.charAt(currPos);
            if(currPos +1 < strLength && currChar == '\\' && source.charAt(currPos + 1)=='"'){ currPos=currPos + 2;};
        }

        return source.substring(pos,currPos+1);
    }
    private String getDIgit(String source,int pos){
        int currPos = pos;
        int strLength = source.length();
        while(currPos < strLength && source.charAt(currPos) - '0' >=0 &&source.charAt(currPos) - '0' <=9){
            currPos++;
            if(source.charAt(currPos)=='.'){
                currPos++;
            }
        }
        return source.substring(pos,currPos);
    }
}
