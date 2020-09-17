import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;

public class ShuntingYard {
    // Returns true if the input String is "+", "-", "*", "/", or "^"; returns false otherwise
    // You can use isOperator to test if a String is an operator or not
    // e.g. isOperator("+") => true
    //      isOperator("5") => false
    public static boolean isOperator(String op)
    {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("^");
    }

    // Returns true if the input String is an integer; returns false otherwise
    // You can use isNumeric to test if a String is an number or not
    // e.g. isNumeric("+") => false
    //      isNumeric("5") => true
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    // Returns the precedence level of the operator; a higher int
    // represents a higher precedence level
    //
    // Precedence rankings:
    // 0: left paren
    // 1: add/sub
    // 2: multiply/divide
    // 3: exponent
    public static int getPrecedence(String str) {
        if (str.equals("(")) {
            return 0;
        }
        else if (str.equals("+") || str.equals("-")) {
            return 1;
        }
        else if (str.equals("*") || str.equals("/")) {
            return 2;
        }
        else { // if (str.equals("^")) {
            return 3;
        }
    }

    // Returns true if the precedence of the *top* argument is
    // greater than or equal to the precedence of the *op* argument
    public static boolean precedenceCompare(String op, String top) {
        return getPrecedence(top) >= getPrecedence(op);
    }


    // Takes in a string containing a math expression in infix format and outputs a
    // queue containing the tokens in postfix format
    public static Queue<String> convert(String infix) {
        // We need to create two data structures: a queue for our output, and a stack for our operators
        Queue<String> outputQueue = new LinkedList<String>();
        Deque<String> operatorStack = new LinkedList<String>();

        String[] arr = infix.split(" ");

        for (String s : arr) {
            if(isNumeric(s)){
                outputQueue.offer(s);
            } else if(isOperator(s)){
                while (!operatorStack.isEmpty() && precedenceCompare(s, operatorStack.peek())){
                    outputQueue.offer(operatorStack.pop());
                }
                operatorStack.push(s);
            } else if(s.equals("(")){
                operatorStack.push(s);
            } else if(s.equals(")")){
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")){
                    outputQueue.offer(operatorStack.pop());
                }
                operatorStack.pop();
            }


        }
        while (!operatorStack.isEmpty()){
            outputQueue.offer(operatorStack.pop());
        }
        return outputQueue;
    }

    // Takes in a queue of tokens in postfix format, and evaluates the math expression
    // Returns the integer answer
    public static int evaluate(Queue<String> inputQueue)
    {
        // YOUR CODE HERE (TO BE WRITTEN LATER)
        return -1;

    }

    public static void main(String[] args) {
        // Example 1: 3 + 4 * 2 - 1
        // Postfix: [3, 4, 2, *, +, 1, -]
        // Result: 10
        System.out.println("Test 1: ");
        System.out.println("Postfix: " + convert("3 + 4 * 2 - 1"));
        System.out.println("Result: " + evaluate(convert("3 + 4 * 2 - 1")));

        // Example 2: 3 + 4 * 2 - 1 * 5 + 6 / 2 + 3
        // Postfix: [3, 4, 2, *, +, 1, 5, *, -, 6, 2, /, +, 3, +]
        // Result: 12
        System.out.println("\nTest 2: ");
        System.out.println("Postfix: " + convert("3 + 4 * 2 - 1 * 5 + 6 / 2 + 3"));
        System.out.println("Output: " + evaluate(convert("3 + 4 * 2 - 1 * 5 + 6 / 2 + 3")));

        // Example 3: 1 + 2 * 3 - 10 / 5 + 2
        // Postfix: [1, 2, 3, *, +, 10, 5, /, -, 2, +]
        // Result: 7
        System.out.println("\nTest 3: ");
        System.out.println("Postfix: " + convert("1 + 2 * 3 - 10 / 5 + 2"));
        System.out.println("Output: " + evaluate(convert("1 + 2 * 3 - 10 / 5 + 2")));

        // Example 4: 5 * ( 2 + 1 )  / 3 + ( 6 - 5 ) * 3
        // Postfix: [5, 2, 1, +, *, 3, /, 6, 5, -, 3, *, +]
        // Result: 8
        System.out.println("\nTest 4: ");
        System.out.println("Postfix: " + convert("5 * ( 2 + 1 ) / 3 + ( 6 - 5 ) * 3"));
        System.out.println("Output: " + evaluate(convert("5 * ( 2 + 1 ) / 3 + ( 6 - 5 ) * 3")));

        // Example 5: 2 ^ 3 / (2 * 4) + 10
        // Postfix: [2, 3, ^, 2, 4, *, /, 10, +]
        // Result: 11
        System.out.println("\nTest 5: ");
        System.out.println("Postfix: " + convert("2 ^ 3 / ( 2 * 4 ) + 10"));
        System.out.println("Output: " + evaluate(convert("2 ^ 3 / ( 2 * 4 ) + 10")));
    }
}