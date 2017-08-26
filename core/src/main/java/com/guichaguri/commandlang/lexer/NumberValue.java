package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import com.guichaguri.commandlang.parser.Instruction;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public class NumberValue implements Token, Instruction {

    public static NumberValue parse(Lexer lexer) throws IOException {
        StringBuilder builder = new StringBuilder();

        // Add the first character
        builder.append(lexer.peek());

        while(lexer.next()) {
            char c = lexer.peek();

            if(c == '_') {
                // Ignore underlines between numbers
                continue;
            } else if(!Character.isLetterOrDigit(c)) {
                break;
            }

            builder.append(c);
        }

        return new NumberValue(Integer.parseInt(builder.toString()));
    }

    private final int value;

    public NumberValue(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

}
