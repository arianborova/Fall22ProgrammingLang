import java.util.ArrayList;

/**
   The function tokenize(s) takes a string and returns a list of tokens. This program can serve as a starting point for a tokenizer for the toy language used in the project.
*/
   public class Tokenizer {
    public static ArrayList<Token> tokenize(String s, ArrayList<Token> tokens){
        int i = 0;
        while (i < s.length()){
            i = extractToken(s, i, tokens);
            if (i == -1){
                throw new RuntimeException("error");
            }
        }
        return tokens;
    }

    /** extract one token starting at i, add the token to tokens, and return the
        index after the token.
    */
    static int extractToken(String s, int i, ArrayList<Token> tokens){
        if (i == s.length())
            return i;
        if (Character.isDigit(s.charAt(i))){
            int val = 0;
            do {
                val = 10*val + s.charAt(i) - '0';   // Character.digit(s.charAt(i), 10)
                i++;
            } while (i < s.length() && Character.isDigit(s.charAt(i)));
            tokens.add(new Num(val));
            return i;
        } else if (Character.isLetter(s.charAt(i))){
            StringBuilder sb = new StringBuilder();
            do {
                sb.append(s.charAt(i));
                i++;
            } while (i < s.length() && Character.isLetter(s.charAt(i)));
            tokens.add(new Letter(sb.toString()));
            return i;
        } else if (isOperator(s.charAt(i))){
            tokens.add(new Operator(Character.toString(s.charAt(i))));
            i++;
            return i;
        } else {
            return -1;
        }
    }

    static String getToken(int index, ArrayList<Token> tokens) {    // new method to help the Parser class scan each token after tokenizing
      return tokens.get(index).toString().substring(3, tokens.get(index).toString().length() - 1);
    }

    static boolean isOperator(char c){
        switch (c){
        case '+':
        case '-':
        case '*':
        case '/':
        case '=': return true;
        }
        return false;
    }
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        String s = "a10+10x";
        System.out.println(tokenize(s, tokens));
        // System.out.println(getToken(0, tokens));
    }
}

interface Tag {
    int NUM = 0;
    int LETTER = 1;
    int OP = 2;
}

class Token {
    public final int tag;
    public Token(int t){
        tag = t;
    }
}

class Num extends Token {
    public final int value;
    public Num(int v){
        super(Tag.NUM);
        value = v;
    }

    public String toString(){
        return "(" + tag + "," + value + ")";
    }
}

class Letter extends Token {
    public final String lexeme;
    public Letter(String s){
        super(Tag.LETTER);
        lexeme = s;
    }

    public String toString(){
        return "(" + tag + "," + lexeme + ")";
    }
}

class Operator extends Token {
    public final String lexeme;
    public Operator(String s){
        super(Tag.OP);
        lexeme = s;
    }

    public String toString(){
        return "(" + tag + "," + lexeme + ")";
    }

}

// public class Interpreter {
//   public static void main(String[] args) {
//
//
//
//     public static
//   }
