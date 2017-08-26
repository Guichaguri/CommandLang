package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public class Variable implements Token {

    public static boolean isVariable(char c) {
        return c == '$';
    }

    public static Variable parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();

        while(lexer.next()) {
            char c = lexer.peek();

            if(!Character.isLetterOrDigit(c) && c != '_') {
                break;
            }

            builder.append(c);
        }

        if(builder.length() == 0) {
            throw new LexerException("A variable cannot have an empty name");
        }

        return new Variable(builder.toString());
    }

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
