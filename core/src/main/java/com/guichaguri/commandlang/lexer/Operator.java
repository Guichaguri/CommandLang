package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public enum Operator implements Token {

    // Arithmetic Operators
    ADDITIVE("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    REMAINDER("%"),

    // Unary Operators
    INCREMENT("++"),
    DECREMENT("--"),
    INVERTER("!"),

    // Assign Operators
    ASSIGN("="),
    SWAP("<>"),
    ADDITIVE_ASSIGN("+="),
    SUBTRACTION_ASSIGN("-="),
    MULTIPLICATION_ASSIGN("*="),
    DIVISION_ASSIGN("/="),
    REMAINDER_ASSIGN("%="),

    // Relational Operators
    EQUAL("=="),
    DIFFERENT("!="),
    GREATER(">"),
    GREATER_EQUAL(">="),
    LESS("<"),
    LESS_EQUAL("<="),

    // Conditional Operators
    AND("&&"),
    OR("||"),
    TERNARY_IF("?"),
    TERNARY_ELSE(":");

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ||
                c == '!' || c == '>' || c == '<' || c == '&' || c == '|' ||
                c == '?' || c == ':' || c == '=';
    }

    public static Operator parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();

        // Add the first operator character
        builder.append(lexer.peek());

        while(lexer.next()) {
            char c = lexer.peek();

            if(builder.length() >= 2 || !isOperator(c)) {
                break;
            }

            builder.append(c);
        }

        String signal = builder.toString();
        Operator operator = null;

        for(Operator op : Operator.values()) {
            if(op.getSignal().equals(signal)) {
                operator = op;
                break;
            }
        }

        if(operator == null) {
            if(signal.startsWith("//")) {
                parseInlineComment(lexer);
            } else if(signal.startsWith("/*")) {
                parseBlockComment(lexer);
            } else {
                throw new LexerException("Unknown operator: " + signal);
            }
        }

        return operator;
    }

    private static void parseInlineComment(Lexer lexer) throws IOException {
        while(lexer.next()) {
            if(lexer.peek() == '\n') break;
        }
    }

    private static void parseBlockComment(Lexer lexer) throws IOException {
        boolean close = false;

        while(lexer.next()) {
            char c = lexer.peek();

            if(c == '*') {
                close = true;
                continue;
            } else if(close && c == '/') {
                break;
            }

            close = false;
        }
    }

    private final String signal;

    Operator(String signal) {
        this.signal = signal;
    }

    public String getSignal() {
        return signal;
    }
}
