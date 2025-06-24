package org.example;

import org.example.lexer.Lexer;
import org.example.parser.JsonParser;
import org.example.parser.ParseException;
import org.example.tokenizer.JSONTokenizer;
import org.example.tokenizer.Token;
import org.example.utility.ResourceUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // This is not a file system path. It's a CLASSPATH resource path.
        String resourcePath = "/user.json";
        String jsonString;
        try{
            jsonString = ResourceUtils.readFromResource("/user.json");

        }catch (IOException e){
            System.out.println(e.getMessage());
            return ;
        }
//        Lexer lexer = new Lexer(0, "");
        JSONTokenizer jsonTokenizer = new JSONTokenizer();
        JsonParser jsonParser = new JsonParser(jsonTokenizer);
        try{
            Map<String,Object> parseResult= jsonParser.parse(jsonString);
            System.out.println(parseResult);
        }catch (ParseException parseException){
            System.out.println(parseException.getMessage());
        }


    }
}