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
                    tokens.add(new StringValue(builder.toString()));
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
                            tokens.add(new StringValue(builder.toString()));
                            builder = new StringBuilder();
                        }
                        tokens.add(token);
                        continue;
                    }
                }
            }
            escape = false;

            builder.append(c);
        }

        throw new LexerException("The string never closes");
    }

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
