package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import com.guichaguri.commandlang.parser.Instruction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guichaguri
 */
public class StringValue implements Token, Instruction {

    public static boolean isString(char c) {
        return c == '"' || c == '\'';
    }

    public static List<Token> parse(Lexer lexer, char closeChar) throws IOException {
        List<Token> tokens = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean escape = false;

        while(lexer.next()) {
            char c = lexer.peek();

            if(!escape) {
                if(c == closeChar) {
                    // String closed
                    lexer.next(); // Skip the close char

                    if(builder.length() > 0) {
                        // Adds the additive sign if we have anything before
                        if(!tokens.isEmpty()) tokens.add(Operator.ADDITIVE);

                        // Finally adds the last bit of string
                        tokens.add(new StringValue(builder.toString()));
                    }

                    return tokens;
                } else if(c == '\\') {
                    // Escape
                    escape = true;
                    continue;
                } else {
                    // Special tokens
                    Token token = null;

                    if(Variable.isVariable(c)) {
                        token = Variable.parse(lexer);
                    } else if(Color.isColor(c)) {
                        token = Color.parseShort(lexer);
                    } else if(Selector.isSelector(c)) {
                        token = Selector.parse(lexer);
                    }

                    if(token != null) {
                        if(builder.length() > 0) {
                            // Adds the additive sign if we have anything before
                            if(!tokens.isEmpty()) tokens.add(Operator.ADDITIVE);

                            // Adds the last bit of string
                            tokens.add(new StringValue(builder.toString()));

                            // Adds another additive sign
                            tokens.add(Operator.ADDITIVE);

                            // Resets the builder
                            builder = new StringBuilder();
                        }

                        // Adds the new token
                        tokens.add(token);
                        continue;
                    }
                }
            } else {
                c = parseEscaping(lexer, c);
            }
            escape = false;

            builder.append(c);
        }

        throw new LexerException("The string never closes");
    }

    private static char parseEscaping(Lexer lexer, char c) throws IOException {
        switch(c) {
            case 't': return '\t'; // Tab
            case 'b': return '\b'; // Backspace
            case 'n': return '\n'; // Line Break
            case 'r': return '\r'; // Carriage Return
            case 'f': return '\f'; // Formfeed
            case 'u': // Unicode
                StringBuilder builder = new StringBuilder();

                for(int i = 0; i < 4 && lexer.next(); i++) {
                    builder.append(lexer.peek());
                }

                return (char)Integer.parseInt(builder.toString(), 16);
        }

        return c; // Rest of the characters (quotes, backslashes, etc)
    }

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
