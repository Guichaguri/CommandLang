package com.guichaguri.commandlang.lexer;

/**
 * @author Guichaguri
 */
public class LexerException extends RuntimeException {

    public LexerException(String message) {
        super(message);
    }

    public LexerException(Throwable cause) {
        super(cause);
    }
}
