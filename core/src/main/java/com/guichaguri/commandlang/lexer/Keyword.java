package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public class Keyword implements Token {

    public static Keyword parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();

        // Add the first character
        builder.append(lexer.peek());

        while(lexer.next()) {
            char c = lexer.peek();

            if(!Character.isLetterOrDigit(c)) {
                break;
            }

            builder.append(c);
        }

        return new Keyword(builder.toString());
    }

    private final String name;

    public Keyword(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
