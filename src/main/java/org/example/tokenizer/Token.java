package org.example.tokenizer;

public class Token {

    private JSONTokens tokentype;
    private String text;

    public Token(JSONTokens tokentype, String text){
        this.tokentype = tokentype;
        this.text = text;
    }

    public JSONTokens getTokentype() {
        return tokentype;
    }

    public void setTokentype(JSONTokens tokentype) {
        this.tokentype = tokentype;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
