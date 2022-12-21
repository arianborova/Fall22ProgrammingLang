import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Parser
{
    static String input_token;
    static int index;
    static String str;
    static Tokenizer t = new Tokenizer();
    
    static void next_token(String token){               // updated method so that it utilizes the Tokenizer class
      ArrayList<Token> tokens = new ArrayList<Token>();
      str = token;
      str = str.replaceAll("\\s+","");                  // remove whitespace
        t.tokenize(str, tokens);
        if (index >= str.length()){
            input_token = "$";
        } else {
            input_token = t.getToken(index++, tokens);
          }
    }

    static boolean match(String expected_token){
        if (!input_token.equals(expected_token)){
            return false;
        }
        next_token(str);
        return true;
    }

    public static boolean parser(String sen){
        str = sen;
        index = 0;
        next_token(str);
        return exp();
    }

    // E -> T E"
    static boolean exp(){
        switch (input_token){
        case "0":
        case "1":
        case "2":
        case "3":
        case "4":
        case "5":
        case "6":
        case "7":
        case "8":
        case "9":
        case "(":
            if (!term()){
                return false;
            }
            if (!exp_prime()){
                return false;
            }
            return true;
        default:
            return false;
        }
    }

    // E' -> + T E' | - T E' | e
    static boolean exp_prime(){
        switch (input_token){
        case "+":
        case "-":
            next_token(str);
            if (!term()){
                return false;
            }
            if (!exp_prime()){
                return false;
            }
            return true;
        case "$":
        case ")":
            return true;
        default: return false;
        }
    }

    // T -> F T'
    static boolean term(){
        switch (input_token){
        case "0":
        case "1":
        case "2":
        case "3":
        case "4":
        case "5":
        case "6":
        case "7":
        case "8":
        case "9":
        case "(":
            if (!factor()){
                return false;
            }
            if (!term_prime()){
                return false;
            }
            return true;
        default:
            return false;
        }
    }

    // T' -> * F T' | / F T' | e
    static boolean term_prime(){
        switch (input_token){
        case "*":
        case "/":
            next_token(str);
            if (!factor()){
                return false;
            }
            if (!term_prime()){
                return false;
            }
            return true;
        case "+":
        case "-":
        case ")":
        case "$":
            return true;
        default: return false;
        }
    }

    //     F -> 0 | 1 | ... | 9 | (E)
    static boolean factor(){
        switch (input_token){
        case "0":
        case "1":
        case "2":
        case "3":
        case "4":
        case "5":
        case "6":
        case "7":
        case "8":
        case "9":
            next_token(str);
            return true;
        case "(":
            next_token(str);
            if (!exp()){
                return false;
            }
            return match(")");
        default:
            return false;
        }
    }

    public static void main(String[] args) {
        // System.out.println(parser("4*3-1"));
    }
}
