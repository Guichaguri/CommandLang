package com.guichaguri.commandlang.lexer;

import com.guichaguri.commandlang.Lexer;
import java.io.IOException;

/**
 * @author Guichaguri
 */
public enum Punctuation implements Token {

    EXPRESSION_OPEN('('),
    EXPRESSION_CLOSE(')'),

    BLOCK_OPEN('{'),
    BLOCK_CLOSE('}'),

    ARGUMENT_SEPARATOR(','),
    INSTRUCTION_END(';'),

    LINE_SEPARATOR; // A punctuation that's only used to count lines

    public static boolean isPunctuation(char c) {
        for(Punctuation p : values()) {
            if(p.parseable && p.punctuation == c) return true;
        }
        return false;
    }

    public static Punctuation parse(Lexer lexer, char c) throws IOException {
        lexer.next(); // Skip itself

        for(Punctuation p : values()) {
            if(p.parseable && p.punctuation == c) {
                return p;
            }
        }

        return null;
    }

    private final char punctuation;
    private final boolean parseable;

    Punctuation(char punctuation) {
        this.punctuation = punctuation;
        this.parseable = true;
    }

    Punctuation() {
        this.punctuation = 0;
        this.parseable = false;
    }

}
