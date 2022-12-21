import java.util.*;
import java.io.*;

public class Interpreter {
  public static void main(String[] args) throws FileNotFoundException {
    File textFile = new File("sample.txt");
    Scanner sc = new Scanner(textFile);
    String str;
    while (sc.hasNextLine()) {
      str = sc.nextLine();
      System.out.println(isAssignment(str, getExpression(str)));
    }
    sc.close();
    // System.out.println("Identifier: " + getIdentifier(str));
    // System.out.println("Expression: " + getExpression(str));
    // System.out.println(isAssignment(str, getExpression(str)));


}

    // static int Interpret() {   ----->  this should've been a method to actually interpret and then compute the parsed text
    //                                    but i didn't quite understand how the Parser class worked and how to use it to evaluate an assignment
    //                                    as it stands, the program can only parse tokens according to your top-down parser in Parser.java and then check to see if it's a valid assignment
    //
    // }

    static boolean isAssignment(String str, String expr) {      // checks each part of the program to make sure they make up a valid assigment; outputs error messages if any of the "check" methods are false
      Parser p = new Parser();
      if (checkIdentifier(getIdentifier(str)) == false) {
        System.out.println("Error: Unknown identifier type");
        return false;
      } else if (checkEquals(str) == false) {
        System.out.println("Eror: Missing assignment sign/too many assignment signs");
        return false;
      } else if (p.parser(getExpression(expr)) == false) {
        System.out.println("Error: Illegal expression");
        return false;
      } else if (checkSemicolon(str) == false) {
        System.out.println("Error: Missing semicolon/too many semicolons");
        return false;
      } else {
        return true;
      }
    }

    static int countEquals(String str) {        // counts the number of equals signs for the checkEquals method
      int count = 0;

      for(int i = 0; i < str.length(); i++) {
        if(str.charAt(i) == '=') {
          count++;
        }
    }
    return count;
  }

  static boolean checkEquals(String str) {      // makes sure there's only one equals sign for the assignment
    // System.out.print("Checking that there is only one equals sign... ");
    if (countEquals(str) == 1) {
      return true;
    }
    return false;
  }

    static boolean checkSemicolon(String str) {       // makes sure the semicolon is in the proper place
      // System.out.print("Checking that there's only one semicolon and that it's at the end of the assignment... ");
      int count = 0;

      for(int i=0; i < str.length() - 1; i++) {
        if(str.charAt(i) == ';') {
          return false;
        }
    }

      if (str.charAt(str.length() - 1) == ';') {
        return true;
      }

      return false;
  }

  static String getIdentifier(String str) {      // singles out the lefthand identifier of the assignment so that we can check its validity in checkIdentifier()
    for(int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '=') {
        return str.substring(0, i);
      }
    }
    return str;
  }

  static String getExpression(String str) {     // singles out the righthand expression of the assignment so that we can parse it using the parser() method from Parser class
    for(int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == '=') {
        str = str.replaceAll("\\s+","");
        // System.out.println(str);
        if (str.endsWith(";")) {
          String expr = str.substring(i, str.length() - 1);
          return expr;
        }
        return str.substring(i, str.length());
      }
    }
    return str;
  }

  static boolean checkIdentifier(String str) {          // checks the identifier so that it follows the rules
    // System.out.print("Checking that the identifier follows the rules... ");
    str = str.replaceAll("\\s+","");
    if (str.length() >= 1) {
      if (Character.isLetter(str.charAt(0))) {
         for (int i = 1; i < str.length(); i++) {
             char c = str.charAt(i);
         if (!Character.isLetter(c) && !Character.isDigit(c)) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
}
